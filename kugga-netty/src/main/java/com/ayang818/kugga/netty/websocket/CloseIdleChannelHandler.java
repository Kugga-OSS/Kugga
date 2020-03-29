package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.gateway.ConnectionUserMap;
import com.ayang818.kugga.netty.gateway.UserConnectionMap;
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
                String shortId = ctx.channel().id().asShortText();
                String userId = ConnectionUserMap.get(shortId);
                if (userId != null) {
                    ConnectionUserMap.remove(shortId);
                    UserConnectionMap.remove(userId, shortId);
                    chatHandler.handlerRemoved(ctx);
                    logger.info("用户{} 的 {} 号channel连接不可达, 已删除相关缓存", userId, shortId);
                    logger.info(ConnectionUserMap.toStrings());
                    logger.info(UserConnectionMap.toStrings());
                }
            }
        }
    }
}
