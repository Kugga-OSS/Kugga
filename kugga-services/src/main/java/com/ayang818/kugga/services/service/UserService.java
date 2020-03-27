package com.ayang818.kugga.services.service;

import com.ayang818.kugga.services.pojo.model.User;
import com.ayang818.kugga.services.pojo.vo.*;

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

    /**
     * @description 获取某一用户相关信息
     * @param uid
     * @return
     */
    UserVo queryUser(Long uid);

    /**
     * @description 通过关键词查询用户
     * @param keyword keyword 可能是用户名 username 也可能是昵称 displayName
     * @return
     */
    SearchUserVo searchByKeyword(String keyword);

    /**
     * @description 新增一条好友请求, 状态变更见 UserRelationStatus 类
     * @param ownUid
     * @param otherUsername
     * @return
     */
    AddFriendResVo addNewFriend(Long ownUid, String otherUsername);

    /**
     * @description 拉取某个用户收到/发出的好友请求
     * @param uid
     * @param type
     */
    PullFriendRequestVo pullFriendRequest(Long uid, String type);

    /**
     * @description 处理收到的加好友请求
     * @param uid
     * @param otherUsername
     * @param type
     */
    HandleRequestVo handleRequest(Long uid, String otherUsername, String type);

    /**
     * @description 拉取某位用户的好友列表
     * @param uid
     */
    FriendListVo fetchFriendList(Long uid);
}
