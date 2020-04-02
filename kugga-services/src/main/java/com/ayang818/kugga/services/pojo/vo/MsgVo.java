package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/23 15:22
 **/
@Data
@Builder
public class MsgVo {
    Long mid;
    String content;
    Long senderUid;
    Long receiverUid;
    Integer type;
    Integer msgType;
    Date createTime;
    String senderName;
    String receiverName;
}
