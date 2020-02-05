package com.ayang818.kugga.services.service.impl;

import com.ayang818.kugga.services.mapper.UserMapper;
import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.model.UserExample;
import com.ayang818.kugga.services.pojo.vo.LoginVo;
import com.ayang818.kugga.services.pojo.vo.RegisterVo;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public RegisterVo register(User newUser) {
        newUser.setPassword(EncryptUtil.encrypt(newUser.getPassword(), newUser.getSalt()));
        logger.info(newUser.toString());
        try {
            userMapper.insertSelective(newUser);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RegisterVo.builder().message("fail").state(0).build();
        }
        return RegisterVo.builder().message("success").state(1).build();
    }

    @Override
    public LoginVo login(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users;
        try {
            users = userMapper.selectByExample(userExample);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return LoginVo.builder().message("服务端错误").state(3).build();
        }
        if (users != null && users.size() == 1) {
            User user = users.get(0);
            if (EncryptUtil.compare(password, user.getSalt(), user.getPassword())) {
                return LoginVo.builder().message("登陆成功").state(1).build();
            } else {
                return LoginVo.builder().message("密码错误").state(0).build();
            }
        }
        return LoginVo.builder().message("用户名不存在").state(0).build();
    }
}
