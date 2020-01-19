package com.ayang818.kugga.starter.pojo;

import lombok.Data;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/18 11:55
 **/
@Data
public class ResultDto {
    String status;
    Object error;
    Object data;

    public ResultDto(String status, Object error, Object data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }
}
