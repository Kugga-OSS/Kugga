package com.ayang818.kugga.netty.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/31 15:34
 **/
// @Component
// public class EmbededRedis {
//
//     @Value("${spring.redis.port}")
//     private int redisPort;
//
//     private RedisServer redisServer;
//
//     @PostConstruct
//     public void startRedis() throws IOException {
//         redisServer = RedisServer.builder().setting("maxheap 200m").port(redisPort).setting("bind localhost").build();
//         redisServer.start();
//     }
//
//     @PreDestroy
//     public void stopRedis() {
//         redisServer.stop();
//     }
// }