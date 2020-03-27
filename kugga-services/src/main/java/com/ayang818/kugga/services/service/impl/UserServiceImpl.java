package com.ayang818.kugga.services.service.impl;

import com.ayang818.kugga.services.enums.UserRelationStatus;
import com.ayang818.kugga.services.mapper.UserExtMapper;
import com.ayang818.kugga.services.mapper.UserMapper;
import com.ayang818.kugga.services.mapper.UserRelationMapper;
import com.ayang818.kugga.services.pojo.JwtSubject;
import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.model.UserExample;
import com.ayang818.kugga.services.pojo.model.UserRelation;
import com.ayang818.kugga.services.pojo.model.UserRelationExample;
import com.ayang818.kugga.services.pojo.vo.*;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.utils.EncryptUtil;
import com.ayang818.kugga.utils.JsonUtil;
import com.ayang818.kugga.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/23 15:23
 **/
@Component
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserExtMapper userExtMapper;

    @Autowired
    UserRelationMapper userRelationMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final Integer DEFAULT_EXPIRED_TIME = 1000 * 3600 * 24 * 7;

    @Override
    public RegisterVo register(User newUser) {
        try {
            userMapper.insertSelective(newUser);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RegisterVo.builder().message("用户名已存在").state(0).build();
        }
        return RegisterVo.builder().message("注册成功").state(1).build();
    }

    @Override
    public LoginVo login(String username, String password) {
        // check username
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users;
        try {
            users = userMapper.selectByExample(userExample);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return LoginVo.builder().message("服务端错误").state(3).build();
        }
        // check password
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            // 将传入的密码加盐后比较存在数据库中已经加密的密码
            if (EncryptUtil.compare(password, user.getSalt(), user.getPassword())) {
                // generate json web token, jwt`s payload include UID, and set expired time as 7 days
                String jwt = jwtUtil.createJWT("kugga", JsonUtil.toJson(new JwtSubject(user.getUid())), DEFAULT_EXPIRED_TIME);
                // set redis cache expired time as 7 days
                stringRedisTemplate.opsForValue().set(jwt, String.valueOf(user.getUid()), DEFAULT_EXPIRED_TIME, TimeUnit.SECONDS);
                return LoginVo.builder().message("登陆成功").jwt(jwt).state(1).build();
            } else {
                return LoginVo.builder().message("密码错误").state(0).build();
            }
        }
        return LoginVo.builder().message("用户名不存在").state(0).build();
    }

    @Override
    public UserVo queryUser(Long uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        if (user == null) {
            return UserVo.builder().state(0).build();
        }
        return UserVo.builder()
                .state(1)
                .avatar(user.getAvatar())
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .build();
    }

    @Override
    public SearchUserVo searchByKeyword(String keyword) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(keyword);
        userExample.or().andDisplayNameLike("%" + keyword + "%");
        List<User> tmp = userMapper.selectByExample(userExample);
        List<UserVo> resList = new ArrayList<>(tmp.size());
        for (User user : tmp) {
            UserVo userVo = UserVo.builder()
                    .userName(user.getUsername())
                    .displayName(user.getDisplayName())
                    .avatar(user.getAvatar())
                    .email(user.getEmail())
                    .build();
            resList.add(userVo);
        }
        return SearchUserVo.builder()
                .state(1)
                .resList(resList)
                .build();
    }

    @Override
    public AddFriendResVo addNewFriend(Long ownerUid, String otherUsername) {
        // 查找第二方用户的UID
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(otherUsername);
        List<User> users = userMapper.selectByExample(userExample);
        User other = null;
        if (users.size() > 0 && users.get(0) != null) {
            other = users.get(0);
        } else {
            return AddFriendResVo.builder()
                    .state(2)
                    .message("该用户已注销")
                    .build();
        }
        // 不能添加自己为好友
        if (other.getUid().equals(ownerUid)) {
            return AddFriendResVo.builder()
                    .state(2)
                    .message("不能添加自己为好友")
                    .build();
        }
        // 插入前先检查是否已经有添加记录了
        UserRelationExample example = new UserRelationExample();
        example.createCriteria()
                .andOwnerUidEqualTo(ownerUid)
                .andOtherUidEqualTo(other.getUid());
        List<UserRelation> checkOwn = userRelationMapper.selectByExample(example);
        example.clear();
        example.createCriteria()
                .andOwnerUidEqualTo(other.getUid())
                .andOtherUidEqualTo(ownerUid);
        List<UserRelation> checkOther = userRelationMapper.selectByExample(example);
        // 若有记录了，则根据状态不同做处理
        if (checkOwn.size() == 1 && checkOther.size() == 1) {
            UserRelation cntOwnRecord = checkOwn.get(0);
            UserRelation cntOtherRecord = checkOther.get(0);
            Byte ownerStatus = cntOwnRecord.getPass();
            Byte otherStatus = cntOtherRecord.getPass();

            final byte wait = UserRelationStatus.WAITING;
            final byte success = UserRelationStatus.SUCCESS;
            final byte fail = UserRelationStatus.FAIL;

            if (ownerStatus == success) {
                // 双方已经是好友了
                if (otherStatus == success) {
                    return AddFriendResVo.builder()
                            .state(1)
                            .message("你们已经是好友啦")
                            .build();
                }
                // 对方尚未处理请求
                if (otherStatus == wait) {
                    return AddFriendResVo.builder()
                            .state(1)
                            .message("已发送请求，等待对方处理，请不要重复发送请求")
                            .build();
                }
                // 对方已拒绝，重新发送添加请求
                if (otherStatus == fail) {
                    Date createTime = new Date(System.currentTimeMillis());
                    // 更新对方状态为等待和插入时间
                    updateUserRelationStatus(other.getUid(), ownerUid, wait, createTime);
                    // 更新自己状态为成功和插入时间
                    updateUserRelationStatus(ownerUid, other.getUid(), success, createTime);
                    return AddFriendResVo.builder()
                            .state(1)
                            .message("已重新发送请求，等待对方处理")
                            .build();
                }
            }
            // 若对方已经同意，并且自己为未处理或未同意，此时直接添加为好友
            if (otherStatus == success && (ownerStatus == wait || ownerStatus == fail)) {
                Date createTime = new Date(System.currentTimeMillis());
                // 更新对方状态为成功和插入时间
                updateUserRelationStatus(other.getUid(), ownerUid, success, createTime);
                // 更新自己状态为成功和插入时间
                updateUserRelationStatus(ownerUid, other.getUid(), success, createTime);

                return AddFriendResVo.builder()
                        .state(1)
                        .message("你们已经成功成为好友")
                        .build();
            }
        }

        UserRelation ownerRelation = new UserRelation();
        UserRelation otherRelation = new UserRelation();
        Date createTime = new Date(System.currentTimeMillis());

        // 插入请求方关系
        ownerRelation.setOwnerUid(ownerUid);
        ownerRelation.setOtherUid(other.getUid());
        ownerRelation.setCreateTime(createTime);
        ownerRelation.setPass(UserRelationStatus.SUCCESS);
        ownerRelation.setSender(true);
        userRelationMapper.insertSelective(ownerRelation);

        // 插入被请求方关系
        otherRelation.setOwnerUid(other.getUid());
        otherRelation.setOtherUid(ownerUid);
        otherRelation.setCreateTime(createTime);
        otherRelation.setPass(UserRelationStatus.WAITING);
        otherRelation.setSender(false);
        userRelationMapper.insertSelective(otherRelation);

        return AddFriendResVo.builder()
                .state(1)
                .message("已发送好友请求")
                .build();
    }

    @Override
    public PullFriendRequestVo pullFriendRequest(Long uid, String type) {
        final String owner = "owner";
        final String other = "other";
        if (!owner.equals(type) && !other.equals(type)) {
            return PullFriendRequestVo.builder()
                    .state(2)
                    .build();
        }
        // 拉取我发出的好友请求, 这里需要的是我向谁发起的请求，以及他应答的状态，
        // 他的应答状态是在以他为owner，我为other的情况，由于是我发起的请求，所以sender=fasle
        // 这样就可以通过一次查询获取我需要的数据
        if (owner.equals(type)) {
            UserRelationExample example = new UserRelationExample();
            example.createCriteria()
                    .andOtherUidEqualTo(uid)
                    .andSenderEqualTo(false);
            List<UserRelation> sentRequests = userRelationMapper.selectByExample(example);
            return PullFriendRequestVo.builder()
                    .state(1)
                    .relations(generateVofromUserRelations(sentRequests, owner))
                    .build();
        }
        // 拉取我收到的好友请求，这里需要的是谁向我发的请求，以及我的应答状态
        // 我的应答状态是在以我为owner，他为other，由于是我收到的请求，所以sender=false
        // 这样也可以通过一次查询获取我需要的数据
        else {
            UserRelationExample example = new UserRelationExample();
            example.createCriteria()
                    .andOwnerUidEqualTo(uid)
                    .andSenderEqualTo(false);
            List<UserRelation> receiveRequests = userRelationMapper.selectByExample(example);
            return PullFriendRequestVo.builder()
                    .state(1)
                    .relations(generateVofromUserRelations(receiveRequests, other))
                    .build();
        }
    }

    @Override
    public HandleRequestVo handleRequest(Long ownerUid, String otherUsername, String type) {
        final String agree = "agree";
        final String reject = "reject";
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(otherUsername);
        List<User> users = userMapper.selectByExample(example);
        User other = null;
        if (users.size() > 0) {
            other = users.get(0);
        } else {
            return HandleRequestVo.builder()
                    .state(2)
                    .message("对方账号已删除")
                    .build();
        }
        Date current = new Date(System.currentTimeMillis());
        if (agree.equals(type)) {
            updateUserRelationStatus(ownerUid, other.getUid(), UserRelationStatus.SUCCESS, current);
        } else if (reject.equals(type)) {
            updateUserRelationStatus(ownerUid, other.getUid(), UserRelationStatus.FAIL, current);
        } else {
            return HandleRequestVo.builder()
                    .state(2)
                    .message("非法操作！")
                    .build();
        }
        return HandleRequestVo.builder()
                .state(1)
                .message(agree.equals(type) ? "添加好友成功！" : "拒绝成功！")
                .build();
    }

    @Override
    public FriendListVo fetchFriendList(Long uid) {
        UserRelationExample example = new UserRelationExample();
        // 这里的逻辑是需要判断一个人是不是你的好友，那么如果你处在关系第二方，并且这段关系的状态为成功的时，
        // 那么这段关系的拥有方一定是你的好友
        example.createCriteria()
                .andOtherUidEqualTo(uid)
                .andPassEqualTo(UserRelationStatus.SUCCESS);
        List<UserRelation> userRelations = userRelationMapper.selectByExample(example);
        List<Long> uidList = new ArrayList<>(userRelations.size());
        for (UserRelation relation : userRelations) {
            uidList.add(relation.getOwnerUid());
        }
        List<UserVo> userVoList = new ArrayList<>();
        List<User> userList = userExtMapper.selectAllByUid(uidList);
        for (User user : userList) {
            UserVo userVo = UserVo.builder()
                    .userName(user.getUsername())
                    .displayName(user.getDisplayName())
                    .avatar(user.getAvatar())
                    .email(user.getEmail())
                    .build();
            userVoList.add(userVo);
        }
        return FriendListVo.builder()
                .state(1)
                .friendList(userVoList)
                .build();
    }

    private void updateUserRelationStatus(Long ownerUid, Long otherUid, Byte status, Date current) {
        UserRelation userRelation = new UserRelation();
        userRelation.setPass(status);
        userRelation.setCreateTime(current);

        UserRelationExample userRelationExample = new UserRelationExample();
        userRelationExample.createCriteria()
                .andOwnerUidEqualTo(ownerUid)
                .andOtherUidEqualTo(otherUid);
        userRelationMapper.updateByExampleSelective(userRelation, userRelationExample);
    }

    /**
     * @description 根据List\<UserRelation> 生成 List\<UserRelationVo>，优化了前一个使用for循环内做数据库查询的方式，改为了用in做查询
     * @param list
     * @param type
     * @return
     */
    private List<UserRelationVo> generateVofromUserRelations(List<UserRelation> list, String type) {
        if (list.size() == 0) return new ArrayList<>();
        final String owner = "owner";
        List<Long> uidList = new ArrayList<>(list.size());
        List<UserRelationVo> resList = new ArrayList<>(list.size());
        if (owner.equals(type)) {
            for (UserRelation userRelation : list) {
                uidList.add(userRelation.getOwnerUid());
            }
        } else {
            for (UserRelation userRelation : list) {
                uidList.add(userRelation.getOtherUid());
            }
        }
        if (list.size() == 0) return resList;
        List<User> users = userExtMapper.selectAllByUid(uidList);
        for (UserRelation relation : list) {
            Long targetId = owner.equals(type) ? relation.getOwnerUid() : relation.getOtherUid();
            for (User tmp : users) {
                if (tmp.getUid().equals(targetId)) {
                    UserVo userVo = UserVo.builder()
                            .userName(tmp.getUsername())
                            .avatar(tmp.getAvatar())
                            .displayName(tmp.getDisplayName())
                            .email(tmp.getEmail())
                            .build();
                    UserRelationVo userRelationVo = UserRelationVo.builder()
                            .other(userVo)
                            .createTime(relation.getCreateTime())
                            .status(relation.getPass())
                            .build();
                    resList.add(userRelationVo);
                    break;
                }
            }
        }
        return resList;
    }
}
