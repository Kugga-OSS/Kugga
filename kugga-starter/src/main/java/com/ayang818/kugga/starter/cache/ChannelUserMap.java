package com.ayang818.kugga.starter.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 杨丰畅
 * @description 维护ChannelID与UserId的hash映射
 * @date 2020/1/14 22:00
 **/
public class ChannelUserMap {

    /**
     * Key : channel的短ID, Value : user的id
     */
    private static final Map<String, String> MAP = new ConcurrentHashMap<>();

    public static void put(String channelShortId, String userId) {
        MAP.put(channelShortId, userId);
    }

    public static String get(String channelShortId) {
        return MAP.get(channelShortId);
    }

}
