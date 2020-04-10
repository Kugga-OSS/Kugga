package com.ayang818.kugga.services.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/9 22:19
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
    Integer contentType;
    /**
     * 消息类型 : ACK、发送新消息、心跳包、注册连接等,
     */
    Integer msgType;
}
