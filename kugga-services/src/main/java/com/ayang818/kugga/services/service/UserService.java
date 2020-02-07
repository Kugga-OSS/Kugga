package com.ayang818.kugga.services.service;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.LoginVo;
import com.ayang818.kugga.services.pojo.vo.RegisterVo;

/**
 * @author 杨丰畅
 * @description 用户对象的操作
 * @date 2020/1/23 15:23
 **/
public interface UserService {
    /**
     * @description 注册
     * @param newUser
     * @return RegisterVo
     */
    RegisterVo register(User newUser);

    /**
     * @description 登录
     * @param username
     * @param password
     * @return LoginVo
     */
    LoginVo login(String username, String password);
}
