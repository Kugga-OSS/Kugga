package com.ayang818.kugga.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description NettyWebSocket服务启动类
 * @date 2020/1/12 21:27
 **/
public enum WebSocketServer {
    // WebSocketServer的单例
    INSTANCE();

    ChannelFuture future;

    private static final Integer PORT = 10086;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);


    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketInitializer());

            this.future = server.bind(PORT).sync();
            LOGGER.info("WebSocket 启动, 运行在 ws://localhost:{}", PORT);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();
        }
    }

    public ChannelFuture getFuture() {
        return this.future;
    }
}
