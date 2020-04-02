package com.ayang818.kugga.services.enums;

public interface MsgType {
    /**
     * 上线请求
     */
    int ONLINE = 1;

    /**
     * 发送新消息
     */
    int NEWMSG = 2;

    /**
     * 接收方的消息 ACK
     */
    int ACK = 3;

    /**
     * 连接的心跳检测
     */
    int HEARTBEAT = 4;

    /**
     * 好友请求，在线推送
     */
    int FRIEND_REQUEST = 5;
}
