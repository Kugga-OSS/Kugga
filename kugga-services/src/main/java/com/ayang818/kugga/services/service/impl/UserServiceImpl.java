package com.ayang818.kugga.services.service.impl;

import com.ayang818.kugga.services.enums.UserRelationStatus;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    // 更新对方状态和插入时间
                    updateUserRelationStatus(other.getUid(), ownerUid, wait, createTime);
                    // 更新自己状态和插入时间
                    updateUserRelationStatus(ownerUid, other.getUid(), success, createTime);
                    return AddFriendResVo.builder()
                            .state(1)
                            .message("已重新发送请求，等待对方处理")
                            .build();
                }
                // 若对方已经同意，并且自己未同意，此时直接添加为好友
                if (ownerStatus == wait || ownerStatus == fail) {
                    Date createTime = new Date(System.currentTimeMillis());
                    // 更新对方状态和插入时间
                    updateUserRelationStatus(other.getUid(), ownerUid, success, createTime);
                    // 更新自己状态和插入时间
                    updateUserRelationStatus(ownerUid, other.getUid(), success, createTime);

                    return AddFriendResVo.builder()
                            .state(1)
                            .message("你们已经成功成为好友")
                            .build();
                }
            }
        }

        UserRelation ownRelation = new UserRelation();
        UserRelation otherRelation = new UserRelation();
        Date createTime = new Date(System.currentTimeMillis());

        // 插入请求方关系
        ownRelation.setOwnerUid(ownerUid);
        ownRelation.setOtherUid(other.getUid());
        ownRelation.setCreateTime(createTime);
        ownRelation.setPass(UserRelationStatus.SUCCESS);


        // 插入被请求方关系
        otherRelation.setOwnerUid(other.getUid());
        otherRelation.setOtherUid(ownerUid);
        otherRelation.setCreateTime(createTime);
        otherRelation.setPass(UserRelationStatus.WAITING);
        userRelationMapper.insertSelective(otherRelation);

        return AddFriendResVo.builder()
                .state(1)
                .message("已发送好友请求")
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
}
