package com.ayang818.kugga.netty.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 杨丰畅
 * @description 维护UserId与ChannelID的hash映射，用作在线推送的作用
 * @date 2020/1/14 22:00
 **/
public class ChannelUserMap {

    /**
     * Key : user的id, Value : channel的短ID的集合(可能使用多个端登录，所以使用Set维护)
     */
    private static final Map<String, Set<String>> MAP = new ConcurrentHashMap<>();

    public static void putIfAbsent(String userId, String channelShortId) {
        Set<String> channelIdSet = get(userId);
        if (channelIdSet != null && channelIdSet.size() != 0) {
            // 若在用户的channel集合中，不做处理；否则添加到集合中
            channelIdSet.add(channelShortId);
            MAP.put(userId, channelIdSet);
            return;
        }
        channelIdSet = new HashSet<>();
        channelIdSet.add(channelShortId);
        MAP.put(userId, channelIdSet);
    }

    public static Set<String> get(String userId) {
        return MAP.get(userId);
    }

    /**
     * @param userId
     * @description 某个客户端断开连接后的删除机制，若是该客户端是这个用户的最后一个在线客户端，
     * 那么就从在线集合中删除这个用户；若不是最后一个客户端，那么就从channelId集合中删除这个channelId
     */
    public static void remove(String userId, String channelShortId) {
        Set<String> channelIdSet = get(userId);
        // channelId集合不为空
        if (channelIdSet != null) {
            // 最后一个客户端
            if (channelIdSet.size() <= 1) {
                MAP.remove(userId);
                return;
            } else {
                channelIdSet.remove(channelShortId);
                MAP.put(userId, channelIdSet);
                return;
            }
        }
        MAP.remove(userId);
    }

    /**
     * @param userId
     * @return 是否包含这个用户
     */
    public static Boolean ifAbsent(String userId) {
        return MAP.containsKey(userId);
    }

    public static String toStrings() {
        return MAP.toString();
    }
}
