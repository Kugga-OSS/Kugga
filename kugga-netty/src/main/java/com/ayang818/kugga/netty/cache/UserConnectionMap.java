package com.ayang818.kugga.netty.cache;

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
    @Builder
    @Data
    public static class Connection {
        private String channelShortId;
        private String channelLongId;

        @Override
        public boolean equals(Object connection) {
            if (this == connection) {
                return true;
            }
            if (connection == null || getClass() != connection.getClass()) {
                return false;
            }
            Connection that = (Connection) connection;
            return Objects.equals(channelShortId, that.channelShortId) &&
                    Objects.equals(channelLongId, that.channelLongId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(channelShortId, channelLongId);
        }
    }

    /**
     * Key : user的id, Value : Connection的短ID的集合(可能使用多个端登录，所以使用Set维护)
     */
    private static final Map<String, Set<Connection>> MAP = new ConcurrentHashMap<>(800);

    public static void put(String userId, String channelShortId) {
        Set<Connection> channelIdSet = get(userId);
        Connection connection = Connection.builder().channelShortId(channelShortId).build();
        // 若在用户的channel集合中，不做处理；否则添加到集合中
        if (channelIdSet != null && channelIdSet.size() != 0) {
            channelIdSet.add(connection);
            MAP.put(userId, channelIdSet);
            return;
        }
        channelIdSet = new HashSet<>();
        channelIdSet.add(connection);
        MAP.put(userId, channelIdSet);
    }

    public static Set<Connection> get(String userId) {
        return MAP.get(userId);
    }

    /**
     * @param userId
     * @description 某个客户端断开连接后的删除机制，若是该连接是这个用户的最后一个在线连接，
     * 那么就从在线连接集合中删除这个用户；若不是最后一个连接，那么就从该用户的链接集合删除该连接
     */
    public static void remove(String userId, String channelShortId) {
        Set<Connection> channelIdSet = get(userId);
        // channelId集合不为空
        if (channelIdSet != null && channelIdSet.size() >= 1) {
            channelIdSet.remove(Connection.builder().channelShortId(channelShortId).build());
            MAP.put(userId, channelIdSet);
        }
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
