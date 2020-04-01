package com.ayang818.kugga.netty.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * @author 杨丰畅
 * @description TODO 将ConnectionUserMap 和 UserConnectionMap 整合到一起，将网关中的常见动作封装成方法，简便作为SDK的调用
 * @date 2020/3/29 20:58
 **/
public class Gateway {

    private static final Logger logger = LoggerFactory.getLogger(Gateway.class);

    /**
     * @description 将用户从网关中下线，这里是已经知道了channelId和它的uid的情况
     * @param channelId
     * @param uid
     */
    public static void offline(String channelId, String uid) {
        ConnectionUserMap.remove(channelId);
        UserConnectionMap.remove(uid, channelId);
        logger.info("用户 {} 已在网关下线", uid);
    }

    /**
     * @param channelId 将用户从网关中下线，这里是只知道channelId
     */
    public static void offline(String channelId) {
        String uid;
        if ((uid = Gateway.getUid(channelId)) != null) {
            offline(channelId, uid);
        }
    }

    /**
     * @description 将用户在网关中上线，最多允许三个在线用户
     * @param channelId
     * @param uid
     */
    public static void online(String channelId, String uid) {
        Set<String> channelIdSet = getChannelIdSet(uid);
        if (channelIdSet.size() >= 3) {
            // 删除一个
            Iterator<String> iterator = channelIdSet.iterator();
            String removeChannelId = iterator.next();
            iterator.remove();
            offline(removeChannelId, uid);
        }
        UserConnectionMap.put(uid, channelId);
        ConnectionUserMap.put(channelId, uid);
        logger.info("用户 {} 已在网关上线", uid);
    }

    /**
     * @description 从网关中获取对应 channelId 的用户 uid
     * @param channelId
     * @return
     */
    public static String getUid(String channelId) {
        return ConnectionUserMap.get(channelId);
    }

    /**
     * @description 从网关中获取对应 uid 的 channelId 集合
     * @param uid
     * @return
     */
    public static Set<String> getChannelIdSet(String uid) {
        return UserConnectionMap.get(String.valueOf(uid));
    }

    public static void show() {
        logger.info("uid-channel 映射表 {}", UserConnectionMap.toStrings());
        logger.info("channel-uid 映射表 {}", ConnectionUserMap.toStrings());
    }
}
