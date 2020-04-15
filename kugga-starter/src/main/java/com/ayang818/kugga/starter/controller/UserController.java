package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.*;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.starter.enums.VoUtil;
import com.ayang818.kugga.starter.pojo.ResultDto;
import com.ayang818.kugga.utils.EncryptUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
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
    public ResultDto getUser(HttpServletRequest req, HttpServletResponse res,
                             @RequestParam(value = "username", required = false) String username) {
        if (username != null) {
            UserVo userVo = userService.queryUser(username);
            return VoUtil.judge(userVo);
        }
        Long uid = (Long) req.getAttribute("uid");
        if (uid == null) return VoUtil.getFailDefault();
        UserVo userVo = userService.queryUser(uid);
        return VoUtil.judge(userVo);
    }

    @ApiOperation("根据用户名/昵称搜索用户列表")
    @RequestMapping(value = "/auth_api/user/search", method = RequestMethod.POST)
    public ResultDto searchUser(@RequestParam("keyword") String keyword) {
        SearchUserVo searchUserVo = userService.searchByKeyword(keyword);
        return VoUtil.judge(searchUserVo);
    }

    @ApiOperation("某一位用户向另一位用户发送好友请求")
    @RequestMapping(value = "/auth_api/user/add", method = RequestMethod.POST)
    public ResultDto addNewFriend(HttpServletRequest req,
                                  @RequestParam("otherUsername") String username) {
        Long uid = (Long) req.getAttribute("uid");
        AddFriendResVo addFriendResVo = userService.addNewFriend(uid, username);
        return VoUtil.judge(addFriendResVo);
    }

    @ApiOperation("拉取某个用户收到/发出的好友请求，其中type参数为枚举字符串，若为owner则拉取自己发出的请求，为other则拉取自己收到的请求")
    @RequestMapping(value = "/auth_api/user/friendRequest", method = RequestMethod.GET)
    public ResultDto pullRequest(HttpServletRequest req, @RequestParam("type") String type) {
        Long uid = (Long) req.getAttribute("uid");
        PullFriendRequestVo pullFriendRequestVo = userService.pullFriendRequest(uid, type);
        return VoUtil.judge(pullFriendRequestVo);
    }

    @ApiOperation("处理自己收到的请求，其中type参数为枚举字符串，若为agree则同意加为好友，为reject则拒绝")
    @RequestMapping(value = "/auth_api/user/friendRequest", method = RequestMethod.POST)
    public ResultDto handleRequest(HttpServletRequest req,
                                   @RequestParam("type") String type,
                                   @RequestParam("otherUsername") String otherUsername) {
        Long uid = (Long) req.getAttribute("uid");
        HandleRequestVo handleRequestVo = userService.handleRequest(uid, otherUsername, type);
        return VoUtil.judge(handleRequestVo);
    }

    @ApiOperation("拉取某位用户的好友列表")
    @RequestMapping(value = "/auth_api/user/friendList", method = RequestMethod.GET)
    public ResultDto fetchFriendList(HttpServletRequest req) {
        Long uid = (Long) req.getAttribute("uid");
        FriendListVo friendListVo = userService.fetchFriendList(uid);
        return VoUtil.judge(friendListVo);
    }

    @ApiOperation("用户上传头像")
    @RequestMapping(value = "/auth_api/user/avatar", method = RequestMethod.POST)
    public ResultDto uploadAvatar(HttpServletRequest req,
                                  @RequestParam("file") MultipartFile file) {
        Long uid = (Long) req.getAttribute("uid");
        UploadAvatarVo uploadAvatarVo = userService.uploadAvatar(uid, file);
        return VoUtil.judge(uploadAvatarVo);
    }

    @ApiOperation("拉取最近联系人的列表")
    @RequestMapping(value = "/auth_api/user/recentChat", method = RequestMethod.GET)
    public ResultDto pullRecentChatList(HttpServletRequest req) {
        Long uid = (Long) req.getAttribute("uid");
        FriendListVo friendListVo = userService.pullRecentChatList(uid);
        return VoUtil.judge(friendListVo);
    }

    @ApiOperation("修改密码接口")
    @RequestMapping(value = "/auth_api/user/password", method = RequestMethod.POST)
    public ResultDto changePass(HttpServletRequest req,
                                @RequestParam("originPass") String originPass,
                                @RequestParam("newPass") String newPass) {
        Long uid = (Long) req.getAttribute("uid");
        GeneralVo generalVo = userService.changePass(uid, originPass, newPass);
        return VoUtil.judge(generalVo);
    }

    @ApiOperation("修改昵称/邮箱接口")
    @RequestMapping(value = "/auth_api/user/nameOrEmail", method = RequestMethod.POST)
    public ResultDto changeDisplayName(HttpServletRequest req,
                                       @RequestParam(value = "displayName", required = false) String displayName,
                                       @RequestParam(value = "email", required = false) String email) {
        Long uid = (Long) req.getAttribute("uid");
        if ((displayName == null || "".equals(displayName)) && (email == null || "".equals(email))) {
            return VoUtil.getFailDefault();
        }
        GeneralVo generalVo = userService.changeDisplayNameOrEmail(uid, displayName, email);
        return VoUtil.judge(generalVo);
    }

}
