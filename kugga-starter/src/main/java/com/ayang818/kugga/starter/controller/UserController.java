package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.LoginVo;
import com.ayang818.kugga.services.pojo.vo.RegisterVo;
import com.ayang818.kugga.services.pojo.vo.SearchUserVo;
import com.ayang818.kugga.services.pojo.vo.UserVo;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.starter.enums.VoUtil;
import com.ayang818.kugga.starter.pojo.ResultDto;
import com.ayang818.kugga.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.form.InputTag;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
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
    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
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
        newUser.setPassword(EncryptUtil.encrypt(newUser.getPassword(), newUser.getSalt()));
        RegisterVo registerVo = userService.register(newUser);
        return VoUtil.judge(registerVo);
    }

    @ApiOperation("用户登录接口")
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public ResultDto login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        LoginVo loginVo = userService.login(username, password);
        return VoUtil.judge(loginVo);
    }

    @ApiOperation("获取某一用户信息")
    @RequestMapping(value = "/auth_api/user", method = RequestMethod.GET)
    public ResultDto getUser(HttpServletRequest req, HttpServletResponse res) {
        Long uid = (Long) req.getAttribute("uid");
        if (uid == null) return VoUtil.getDefault();
        UserVo userVo = userService.queryUser(uid);
        return VoUtil.judge(userVo);
    }

    @ApiOperation("根据用户名/昵称搜索用户")
    @RequestMapping(value = "/auth_api/user/search", method = RequestMethod.POST)
    public ResultDto searchUser(@RequestParam("keyword") String keyword) {
        SearchUserVo searchUserVo = userService.searchByKeyword(keyword);
        return VoUtil.judge(searchUserVo);
    }

    @RequestMapping(value = "/auth_api/chat", method = RequestMethod.GET)
    public String chat() {
        return "hello, you can reach here";
    }
}
