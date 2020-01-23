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
    String msgType;
}
