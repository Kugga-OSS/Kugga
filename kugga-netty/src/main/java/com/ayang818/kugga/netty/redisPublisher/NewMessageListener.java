package com.ayang818.kugga.netty.redisPublisher;

import com.ayang818.kugga.netty.websocket.ChatHandler;
import com.ayang818.kugga.services.pojo.vo.MsgVo;
import com.ayang818.kugga.services.service.impl.MsgServiceImpl;
import com.ayang818.kugga.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/31 13:26
 **/
@Component
public class NewMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String topic = stringRedisSerializer.deserialize(message.getChannel());
        String msgVoString = new String(message.getBody());
        // 生产环境中删除
        // logger.info("Message Received --> pattern: {}，topic:{}，message: {}", new String(bytes), topic, msgVoString);
        MsgVo msgVo = JsonUtil.fromJson(msgVoString, MsgVo.class);

        /* 通过redis的发布订阅模式推送消息到消息接收方 */
        ChatHandler.pushMessageToUser(msgVo.getReceiverUid(), msgVoString);
    }
}
