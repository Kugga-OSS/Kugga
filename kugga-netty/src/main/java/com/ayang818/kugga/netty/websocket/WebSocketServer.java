package com.ayang818.kugga.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description NettyWebSocket服务启动类
 * @date 2020/1/12 21:27
 **/
@Component
public class WebSocketServer {

    @Autowired
    WebSocketInitializer webSocketInitializer;

    ChannelFuture future;

    private static final Integer PORT = 10086;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(webSocketInitializer);

        try {
            this.future = server.bind(PORT).sync();
            logger.info("WebSocket 启动, 运行在 ws://localhost:{}", PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("WebSocket 启动失败");
        }
    }

    public ChannelFuture getFuture() {
        return this.future;
    }
}
