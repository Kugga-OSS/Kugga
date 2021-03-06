package com.ayang818.kugga.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description Channel中初始化工具类
 * @date 2020/1/12 21:33
 **/
@Component
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

    private final ChatHandler chatHandler;

    private final CloseIdleChannelHandler idleChannelHandler;

    @Autowired
    public WebSocketInitializer(ChatHandler chatHandler, CloseIdleChannelHandler idleChannelHandler) {
        this.chatHandler = chatHandler;
        this.idleChannelHandler = idleChannelHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // =========== 用于支持Http协议的handler ===========
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024, true));

        // =========== 用于WebSocket协议 ===========
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(chatHandler);

        // =========== 用于心跳检测 =============
        // 由于有前端有15s一次的心跳检测。所以后端可以适当调小——60s无读写，即判断连接死亡
        pipeline.addLast(new IdleStateHandler(0, 0, 60));
        pipeline.addLast(idleChannelHandler);
    }

}
