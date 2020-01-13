package com.ayang818.kugga.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 12:01
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();

    }

    /**
     * @param ctx
     * @description Handler 生命周期中的一部分，当Handler被加到某个pipeline时执行的动作
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        channels.add(ctx.channel());
    }


    /**
     * @param ctx
     * @description Handler生命周期的一部分，当Handler从某个pipeline删除时执行的动作，默认就是
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        channels.remove(ctx.channel());
    }

}
