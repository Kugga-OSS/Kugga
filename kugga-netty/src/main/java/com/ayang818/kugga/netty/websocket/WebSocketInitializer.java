package com.ayang818.kugga.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author 杨丰畅
 * @description Channel中初始化工具类
 * @date 2020/1/12 21:33
 **/
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024, true));
        // =========== 以上用于支持Http协议的handler

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new ChatHandler());
    }

}
