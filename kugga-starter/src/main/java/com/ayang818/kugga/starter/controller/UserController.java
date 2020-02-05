package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.LoginVo;
import com.ayang818.kugga.services.pojo.vo.RegisterVo;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.starter.enums.Result;
import com.ayang818.kugga.starter.enums.VoUtil;
import com.ayang818.kugga.starter.pojo.ResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 杨丰畅
 * @description 用户操作的相关接口
 * @date 2020/1/18 11:52
 **/
@Api(tags = "用户接口", description = "用户操作的相关接口")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/api/user/register", method = RequestMethod.POST)
    public ResultDto register(@RequestParam("username") String username,
                              @RequestParam("password") String decryptPassword,
                              @RequestParam(value = "displayName", required = false) String displayName,
                              @RequestParam(value = "avatar", required = false) String avatar,
                              @RequestParam("email") String email) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(decryptPassword);
        newUser.setDisplayName(displayName);
        // 取随机10位随机字符串作为盐
        newUser.setSalt(UUID.randomUUID().toString().substring(0, 10));
        newUser.setAvatar(avatar);
        newUser.setEmail(email);
        newUser.setBlocked(false);
        RegisterVo registerVo = userService.register(newUser);
        return VoUtil.judge(registerVo);
    }

    @ApiOperation("用户登录接口")
    @RequestMapping(value = "/api/user/login", method = RequestMethod.GET)
    public ResultDto login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        LoginVo loginVo = userService.login(username, password);
        return VoUtil.judge(loginVo);
    }
}
