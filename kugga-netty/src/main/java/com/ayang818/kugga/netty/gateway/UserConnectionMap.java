package com.ayang818.kugga.netty.gateway;

import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 杨丰畅
 * @description 维护UserId与Connection类的hash映射，用作在线推送的作用
 * @date 2020/1/14 22:00
 **/
public class UserConnectionMap {
    /**
     * Key : user的id, Value : Connection的短ID的集合(可能使用多个端登录，所以使用Set维护)
     */
    private static final Map<String, Set<String>> MAP = new ConcurrentHashMap<>(800);


    public static void put(String userId, String channelShortId) {
        Set<String> channelSet = get(userId);
        channelSet.add(channelShortId);
        MAP.put(userId, channelSet);
    }

    public static Set<String> get(String userId) {
        Set<String> channelSet = MAP.get(userId);
        return channelSet == null ? new HashSet<>() : channelSet;
    }

    /**
     * @param userId
     * @description 某个客户端断开连接后的删除机制，若是该连接是这个用户的最后一个在线连接，
     * 那么就从在线连接集合中删除这个用户；若不是最后一个连接，那么就从该用户的连接集合删除该连接
     */
    public static void remove(String userId, String channelShortId) {
        Set<String> channelIdSet = get(userId);
        // 该用户不止一个连接登录
        if (channelIdSet.size() > 1) {
            channelIdSet.remove(channelShortId);
            MAP.put(userId, channelIdSet);
        }
        // 该用户只有1个连接了，直接从在线集合中删除这个用户
        else if (channelIdSet.size() == 1) {
            MAP.remove(userId);
        }
    }

    /**
     * @param userId
     * @return 检查某个用户是否在线
     */
    public static Boolean isOnline(String userId) {
        return MAP.containsKey(userId);
    }

    public static String toStrings() {
        return MAP.toString();
    }
}
