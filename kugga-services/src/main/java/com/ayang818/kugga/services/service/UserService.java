package com.ayang818.kugga.services.service;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 杨丰畅
 * @description 用户对象的操作
 * @date 2020/1/23 15:23
 **/
public interface UserService {
    /**
     * @param newUser
     * @return RegisterVo
     * @description 注册
     */
    RegisterVo register(User newUser);

    /**
     * @param username
     * @param password
     * @return LoginVo
     * @description 登录
     */
    LoginVo login(String username, String password);

    /**
     * @param uid
     * @return
     * @description 获取某一用户相关信息
     */
    UserVo queryUser(Long uid);

    /**
     * @param keyword keyword 可能是用户名 username 也可能是昵称 displayName
     * @return
     * @description 通过关键词查询用户
     */
    SearchUserVo searchByKeyword(String keyword);

    /**
     * @param ownUid
     * @param otherUsername
     * @return
     * @description 新增一条好友请求, 状态变更见 UserRelationStatus 类
     */
    AddFriendResVo addNewFriend(Long ownUid, String otherUsername);

    /**
     * @param uid
     * @param type
     * @description 拉取某个用户收到/发出的好友请求
     */
    PullFriendRequestVo pullFriendRequest(Long uid, String type);

    /**
     * @param uid
     * @param otherUsername
     * @param type
     * @description 处理收到的加好友请求
     */
    HandleRequestVo handleRequest(Long uid, String otherUsername, String type);

    /**
     * @param uid
     * @description 拉取某位用户的好友列表
     */
    FriendListVo fetchFriendList(Long uid);

    /**
     * @param uid
     * @param avatarFile
     * @return
     * @description 上传头像
     */
    UploadAvatarVo uploadAvatar(Long uid, MultipartFile avatarFile);

    /**
     * @param username
     * @return
     * @description 通过用户名搜索用户
     */
    UserVo queryUser(String username);

    /**
     * @param uid
     * @return List
     * @description 拉取最近联系人
     */
    FriendListVo pullRecentChatList(Long uid);

    /**
     * @description 修改密码
     * @param uid
     * @param originPass
     * @param newPass
     * @return
     */
    GeneralVo changePass(Long uid, String originPass, String newPass);

    /**
     * @description 修改昵称
     * @param uid
     * @param displayName
     * @return
     */
    GeneralVo changeDisplayNameOrEmail(Long uid, String displayName, String email);
}
