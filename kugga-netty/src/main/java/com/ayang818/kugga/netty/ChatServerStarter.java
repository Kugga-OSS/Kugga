package com.ayang818.kugga.netty;

import com.ayang818.kugga.netty.websocket.WebSocketServer;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 17:18
 **/
public class ChatServerStarter {
    public static void main(String[] args) throws InterruptedException {
        WebSocketServer.run();
    }
}
