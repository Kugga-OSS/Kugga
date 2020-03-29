package com.ayang818.kugga.netty.websocket;

import com.ayang818.kugga.netty.enums.MsgType;
import com.ayang818.kugga.netty.gateway.ConnectionUserMap;
import com.ayang818.kugga.netty.gateway.UserConnectionMap;
import com.ayang818.kugga.services.pojo.MsgDto;
import com.ayang818.kugga.services.pojo.vo.MsgVo;
import com.ayang818.kugga.services.service.MsgService;
import com.ayang818.kugga.services.service.UserService;
import com.ayang818.kugga.utils.JsonUtil;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
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
        // 此处 logger 需要在生产环境中删除
        logger.info("收到消息 {}", content);
        Gson paser = JsonUtil.getPaser();
        MsgDto msgDto = JsonUtil.fromJson(content, MsgDto.class);
        // 这条消息的目的，详情见MsgType中的几个枚举类
        int msgType = msgDto.getMsgType();
        String shortId = context.channel().id().asShortText();
        switch (msgType) {
            case MsgType.ONLINE:
                String uid = String.valueOf(msgDto.getSenderUid());
                // 在网关上线
                onlineGateway(shortId, uid);
                // 为用户设置初始为ACK消息列表
                context.channel().attr(NON_ACKED_MAP).set(new ConcurrentHashMap<>(16));
                // 为用户设置私有的消息消息序号，用于作为消息接收方的ACK时候的sequence id
                context.channel().attr(SID_GENERATOR).set(new AtomicLong(0));
                logger.info("用户 {} 已在网关上线", msgDto.getSenderUid());
                break;
            case MsgType.NEWMSG:
                // 消息持久化，并产生redis发布
                MsgVo msgVo = msgService.sendMsg(msgDto);
                logger.info("产生的消息视图为 {}", msgVo);
                // 向发送方回推消息，确认服务器收到消息
                pushMessageToUser(msgVo.getSenderUid(), JsonUtil.toJson(msgVo));
                break;
            case MsgType.ACK:
                // 对某条消息序号进行ACK，若一段时间没有收到，则对消息进行重发
                break;
            case MsgType.HEARTBEAT:
                logger.info("收到心跳包");
                context.channel().writeAndFlush(new TextWebSocketFrame("ping:pong"));
                break;
            default:
                break;
        }

    }

    /**
     * @description 推送消息, 这是个无状态的动作, 所以选择使用static方法修饰
     * @param uid 推送消息目的用户的uid
     * @param jsonMsgVo
     */
    public static void pushMessageToUser(Long uid, String jsonMsgVo) {
        // 从网关中获取该用户在线设备的连接集合
        Set<String> onlineChannelIdSet = UserConnectionMap.get(String.valueOf(uid));
        // 若由用户在线，则推送；否则等待用户上线后拉取
        if (!onlineChannelIdSet.isEmpty()) {
            channels.parallelStream().forEach(channel -> {
                if (onlineChannelIdSet.contains(channel.id().asShortText())) {
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
        String channelId = ctx.channel().id().asShortText();
        String uid = ConnectionUserMap.get(channelId);
        if (uid != null) {
            offlineGateway(channelId, uid);
            logger.info("用户 {} 已在网关下线", uid);
        }
        channels.remove(ctx.channel());
        logger.info("{} channel已删除", channelId);
    }

    /**
     * @description 将用户从网关中下线
     * @param channelId
     * @param uid
     */
    public void offlineGateway(String channelId, String uid) {
        ConnectionUserMap.remove(channelId);
        UserConnectionMap.remove(uid, channelId);
    }

    public void onlineGateway(String channelId, String uid) {
        ConnectionUserMap.put(channelId, uid);
        UserConnectionMap.put(uid, channelId);
    }

}