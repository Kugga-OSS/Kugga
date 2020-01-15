package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.dto.ChatMessageDto;
import com.ayang818.kugga.util.GsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *                               ——————————————————————————
 *                               已经登录，每次进入主界面，且上
 *                              一个连接过期了，重新创建channel。
 *                               ——————————————————————————
 *                                          |
 *                                          |
 *                                          V
 *                               ———————————————————————————
 *                               创建维护用户Id与ChannelShortId
 *                               的ConcurrentHashMap映射。用于
 *                               在线用户的信息维护。
 *                               ————————————————————————————
 *                                          |
 *                                          |
 *                                          V
 *                               ————————————————————————————
 *                               每次Handler收到传入的信息时(
 *                                  传入的信息有几种类别
 *                                  1. 消息
 *                                  2. 心跳包
 *                                  3. 新的Channel连接
 *                               )
 *                               根据消息类别的不同做出不同的行为。
 *                               1. 若是消息，进行消息的存储
 *                               2. 若是新的Channel连接，拉取对应用户的未读消息。
 *                               3. 若是心跳包，不做该Handler做处理。
 *                               ————————————————————————————
 *
 *
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 12:01
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        ChatMessageDto chatMessage = ChatMessageDto.builder()
                .sender(context.channel().id().asShortText())
                .receiver("message")
                .content(content).build();
        channels.writeAndFlush(new TextWebSocketFrame(GsonUtil.toJsonString(chatMessage)));
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
     * @description Handler生命周期的一部分，当Handler从某个pipeline删除时执行的动作
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        channels.remove(ctx.channel());
    }

}
