package com.ayang818.kugga.starter.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 14:14
 **/
@Data
@Builder
public class HelloDto {
    String serverTime;
    String utcTime;
    String message;
    String apiVersion;
}
