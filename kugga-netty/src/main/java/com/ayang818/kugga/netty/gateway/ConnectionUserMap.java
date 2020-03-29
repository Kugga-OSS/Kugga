package com.ayang818.kugga.netty.gateway;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 杨丰畅
 * @description 维护Connection和UserId的映射
 * @date 2020/1/28 14:05
 **/
public class ConnectionUserMap {

    private static final Map<String, String> MAP = new ConcurrentHashMap<>(800);

    public static void put(String channelShortId, String userId) {
        MAP.put(channelShortId, userId);
    }

    /**
     * @description 获取某个连接对应的用户uid，若没有则为空
     * @param channelShortId
     * @return
     */
    public static String get(String channelShortId) {
        return MAP.get(channelShortId);
    }

    /**
     * @description 删除某个连接后对应的动作
     * @param channelShortId
     */
    public static void remove(String channelShortId) {
        MAP.remove(channelShortId);
    }

    public static String toStrings() {
        return MAP.toString();
    }

}
