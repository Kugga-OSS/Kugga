package com.ayang818.kugga.services.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/2/5 14:00
 **/
@Data
@Builder
public class RegisterVo implements Vo{
    String message;
    Integer state;
}
