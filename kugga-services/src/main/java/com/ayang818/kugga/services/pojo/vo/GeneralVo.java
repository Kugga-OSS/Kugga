package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/15 23:37
 **/
@Data
@Builder
public class GeneralVo implements Vo {
    Integer state;
    String message;
}
