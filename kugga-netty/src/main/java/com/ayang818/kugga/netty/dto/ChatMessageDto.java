package com.ayang818.kugga.netty.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 16:46
 **/
@Data
@Builder
public class ChatMessageDto {
    String sender;
    String receiver;
    String content;
}
