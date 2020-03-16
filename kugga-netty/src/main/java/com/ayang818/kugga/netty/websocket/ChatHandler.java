package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.Constant;
import com.ayang818.kugga.netty.cache.ConnectionUserMap;
import com.ayang818.kugga.netty.cache.UserConnectionMap;
import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.pojo.vo.MsgVo;
import com.ayang818.kugga.services.service.MsgService;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.utils.JsonUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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
 *                               3. 若是心跳包，不在该Handler做处理。
 *                               ————————————————————————————
 *
 *
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 12:01
 **/
@ChannelHandler.Sharable
@Component
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    UserService userService;

    @Autowired
    MsgService msgService;

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 未收到ACK的列表
     */
    private static final AttributeKey<ConcurrentHashMap<Long, String>> NON_ACKED_MAP = AttributeKey.newInstance("non_acked_map");

    /**
     * 消息序列生成器  sequence-id-generator
     */
    private static final AttributeKey<AtomicLong> SID_GENERATOR = AttributeKey.newInstance("sid-generator");

    private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        MsgDto msgDto = JsonUtil.fromJson(content, MsgDto.class);
        Integer msgType = msgDto.getMsgType();
        String shortId = context.channel().id().asShortText();

        switch (msgType) {
            case Constant.ONLINE:
                UserConnectionMap.put(String.valueOf(msgDto.getSenderUid()), shortId);
                ConnectionUserMap.put(shortId, String.valueOf(msgDto.getSenderUid()));
                // 为用户设置初始消息列表
                context.channel().attr(NON_ACKED_MAP).set(new ConcurrentHashMap<>(16));
                // 为用户设置私有的消息消息序号
                context.channel().attr(SID_GENERATOR).set(new AtomicLong(0));
                logger.info("用户 {} 已注册到双向映射表, 已经上线", msgDto.getSenderUid());
                break;
            case Constant.NEWMSG:
                // 消息持久化，并产生redis发布
                MsgVo msgVo = msgService.sendMsg(msgDto);
                // 回推消息，确认服务器收到消息
                pushMessageToUser(msgVo.getSenderUid(), JsonUtil.toJson(msgVo));
                break;
            case Constant.ACK:

                break;
            case Constant.HEARTBEAT:
                logger.info("收到心跳包");
                context.channel().writeAndFlush(new TextWebSocketFrame("ping:pong"));
                break;
            default:
                break;
        }

    }

    /**
     * @description 推送消息, 这是个无状态的动作, 所以我选择使用static方法修饰
     * @param uid
     * @param jsonMsgVo
     */
    public static void pushMessageToUser(Long uid, String jsonMsgVo) {
        Set<UserConnectionMap.Connection> connections = UserConnectionMap.get(String.valueOf(uid));
        if (connections != null && !connections.isEmpty()) {
            Set<String> channelShortIdSet = new HashSet<>();
            connections.forEach(connection -> channelShortIdSet.add(connection.getChannelShortId()));
            channels.forEach(channel -> {
                if (channelShortIdSet.contains(channel.id().asShortText())) {
                    channel.writeAndFlush(new TextWebSocketFrame(jsonMsgVo));
                }
            });
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
        logger.info("{} channel已添加", ctx.channel().id().asShortText());
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
        logger.info("{} channel已删除", ctx.channel().id().asShortText());
    }

}