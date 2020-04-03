package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 16:47
 **/
@Data
@Builder
public class MsgListVo implements Vo {
    Integer state;
    List<MsgVo> msgList;
}
