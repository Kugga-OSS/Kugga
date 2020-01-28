package com.ayang818.kugga.netty.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 杨丰畅
 * @description 维护Connection和UserId的映射
 * @date 2020/1/28 14:05
 **/
public class ConnectionUserMap {

    private static final Map<UserConnectionMap.Connection, String> MAP = new ConcurrentHashMap<>(2000);

    public static void put(String channelShortId, String userId) {
        MAP.put(UserConnectionMap.Connection.builder().channelShortId(channelShortId).build(), userId);
    }

    public static String get(String channelShortId) {
        return MAP.get(UserConnectionMap.Connection.builder().channelShortId(channelShortId).build());
    }

    public static void remove(String channelShortId) {
        MAP.remove(UserConnectionMap.Connection.builder().channelShortId(channelShortId).build());
    }

    public static String toStrings() {
        return MAP.toString();
    }

}
