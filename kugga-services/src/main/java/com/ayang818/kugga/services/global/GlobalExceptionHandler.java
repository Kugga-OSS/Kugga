package com.ayang818.kugga.services.global;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/4/3 20:30
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = CustomizeException.class)
    public String serviceException(HttpServletRequest req, CustomizeException e) throws Exception {
        return  "{\"message\": '"+ e.getMessage() +"', status: '500'}";
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String unExceptException(HttpServletRequest req, Exception e) throws Exception {
        return "{\"message\": '服务端错误', status: '500'}";
    }
}
