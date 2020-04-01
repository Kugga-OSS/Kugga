package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.gateway.Gateway;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/28 13:58
 **/
@ChannelHandler.Sharable
@Component
public class CloseIdleChannelHandler extends ChannelDuplexHandler {

    public static final Logger logger = LoggerFactory.getLogger(CloseIdleChannelHandler.class);

    @Autowired
    ChatHandler chatHandler;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                chatHandler.handlerRemoved(ctx);
            }
        }
    }
}
