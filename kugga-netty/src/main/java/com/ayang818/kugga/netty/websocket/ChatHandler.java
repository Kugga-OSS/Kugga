package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.cache.UserChannelMap;
import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.service.MsgService;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.services.service.impl.MsgServiceImpl;
import com.ayang818.kugga.services.service.impl.UserServiceImpl;
import com.ayang818.kugga.utils.GsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

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
@Component
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final UserService userService;

    private final MsgService msgService;

    @Autowired
    public ChatHandler(UserService userService, MsgService msgService) {
        this.userService = userService;
        this.msgService = msgService;
    }

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        // 将接收到的Json转化为ChatMessageDto对象
        MsgDto msgDto = GsonUtil.fromJson(content, MsgDto.class);
        // ================ 随便发一条消息, 将用户注册到在线列表(不会用到生产环境) ===================
        UserChannelMap.putIfAbsent(msgDto.getSenderUid(), context.channel().id().asShortText());
        // LOGGER.info("{}", UserChannelMap.toStrings());
        LOGGER.info("收到消息对象 : {}", msgDto.toString());

        // ================ 消息持久化 ==============
        msgService.sendMsg(msgDto);
        // 取出接受用户用户的在线设备集合
        Set<String> receiverChannelSet = UserChannelMap.get(msgDto.getReceiverUid());
        for (Channel channel : channels) {
            String channelShortId = channel.id().asShortText();
            String processedMessage = String.format("来自%s的消息 : %s", msgDto.getSenderUid(), msgDto.getContent());
            // 推送消息
            if (receiverChannelSet != null && receiverChannelSet.contains(channelShortId)) {
                channel.writeAndFlush(new TextWebSocketFrame(processedMessage));
            }
        }
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
