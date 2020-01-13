package com.ayang818.kugga.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 杨丰畅
 * @description NettyWebSocket服务启动类
 * @date 2020/1/12 21:27
 **/
public class WebSocketServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup slaveGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketInitializer());
        } finally {
            bossGroup.shutdownGracefully();
            slaveGroup.shutdownGracefully();
        }
    }

    public static void Hello() {
        System.out.println("hello");
    }
}
