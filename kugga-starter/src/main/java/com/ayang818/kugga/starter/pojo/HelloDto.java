package com.ayang818.kugga.starter.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 14:14
 **/
@ApiModel("彩蛋实体")
@Data
@Builder
public class HelloDto {
    @ApiModelProperty("服务时间")
    String serverTime;
    @ApiModelProperty("UTC时间")
    String utcTime;
    @ApiModelProperty("返回内容")
    String message;
    @ApiModelProperty("api版本号")
    String apiVersion;
}
