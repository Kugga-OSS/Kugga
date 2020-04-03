package com.ayang818.kugga.services.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 16:59
 **/
@Data
public class MsgTmp {
    Long mid;
    Long ownerUid;
    Long otherUid;
    String content;
    Integer sender;
    Date createTime;
}
