package com.ayang818.kugga.netty;

import com.ayang818.kugga.netty.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description Spring-Netty服务监听器,当spring服务启动时,同时开启Netty服务
 * @date 2020/1/14 15:21
 **/
@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    WebSocketServer webSocketServer;

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyBoot.class);
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            webSocketServer.run();
        }
    }
}