package com.ayang818.kugga.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 杨丰畅
 * @description NettyWebSocket服务启动类
 * @date 2020/1/12 21:27
 **/
public class WebSocketServer {

    private static final Integer PORT = 10086;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    public static void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketInitializer());

            ChannelFuture startFuture = server.bind(PORT).sync();
            LOGGER.info("WebSocket 启动, 运行在 ws://localhost:{}", PORT);

            startFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();
        }
    }
}
