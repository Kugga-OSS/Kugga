package com.ayang818.kugga.services.global;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 20:28
 **/
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }
}
