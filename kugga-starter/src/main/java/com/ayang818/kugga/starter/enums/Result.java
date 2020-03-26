package com.ayang818.kugga.starter.enums;

import com.ayang818.kugga.starter.pojo.ResultDto;

/**
 * @author 杨丰畅
 * @description 通用返回类构造器
 * @date 2020/1/18 11:57
 **/
public class Result {

    public static ResultDto defaultRes() {
        return new ResultDto(Status.UN_AUTHORIZED, null);
    }

    public static ResultDto defaultTrueRes() {
        return new ResultDto(Status.OK, null);
    }

    public static ResultDto ok(Object data) {
        return new ResultDto(Status.OK, data);
    }

    public static ResultDto authenticateFailed(Object data) {
        return new ResultDto(Status.UN_AUTHORIZED, data);
    }

    public static ResultDto serverError(Object error) {
        return new ResultDto(Status.SERVER_ERROR, error);
    }

    public static ResultDto reject(Object error) {
        return new ResultDto(Status.REJECT, error);
    }
}
