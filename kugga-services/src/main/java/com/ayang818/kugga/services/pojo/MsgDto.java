package com.ayang818.kugga.services.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/23 15:31
 **/
@Data
@Builder
public class MsgDto {
    Long senderUid;
    Long receiverUid;
    String content;
    /**
     * 消息内容类型 : 文本、文件、图片等
     */
    String contentType;
    /**
     * 消息类型 : ACK、发送新消息、心跳包、注册连接等
     */
    Integer msgType;
}
