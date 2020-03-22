package com.ayang818.kugga.services.service.impl;

import com.ayang818.kugga.services.mapper.UserMapper;
import com.ayang818.kugga.services.pojo.JwtSubject;
import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.model.UserExample;
import com.ayang818.kugga.services.pojo.vo.LoginVo;
import com.ayang818.kugga.services.pojo.vo.RegisterVo;
import com.ayang818.kugga.services.pojo.vo.UserVo;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.utils.EncryptUtil;
import com.ayang818.kugga.utils.JsonUtil;
import com.ayang818.kugga.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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
                .build();
    }
}
