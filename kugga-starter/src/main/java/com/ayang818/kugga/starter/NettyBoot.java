package com.ayang818.kugga.starter;

import com.ayang818.kugga.netty.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/14 15:21
 **/
@Component
public class NettyBoot implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyBoot.class);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            WebSocketServer.INSTANCE.run();
        }
    }
}
