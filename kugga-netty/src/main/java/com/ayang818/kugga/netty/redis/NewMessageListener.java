package com.ayang818.kugga.netty.redis;

import com.ayang818.kugga.netty.websocket.ChatHandler;
import com.ayang818.kugga.services.service.impl.MsgServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/31 13:26
 **/
@Component
public class NewMessageListener implements MessageListener {

    @Autowired
    ChatHandler chatHandler;

    private static final Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);

    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    private static final RedisSerializer<String> valueSerializer= new GenericToStringSerializer(Object.class);

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String topic = stringRedisSerializer.deserialize(message.getChannel());
        String jsonMsg = valueSerializer.deserialize(message.getBody());
        logger.info("Message Received --> pattern: {}，topic:{}，message: {}", new String(bytes), topic, jsonMsg);

        // chatHandler.pushMessage(null, null, false);
    }
}
