package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.starter.pojo.HelloDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author 杨丰畅
 * @description Kugga服务的彩蛋Controller
 * @date 2020/1/13 14:09
 **/
@Api(tags = "彩蛋接口", description = "服务端的接口彩蛋")
@RestController
public class KuggaHelloController {

    @Value("${project.version}")
    String apiVersion;

    @ApiOperation("根路径的彩蛋")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HelloDto egg() {
        return HelloDto.builder().serverTime(LocalDateTime.now().toString())
                .utcTime(LocalDateTime.now().toString())
                .apiVersion(apiVersion)
                .message("welcome to kugga-server")
                .build();
    }

    @ApiOperation("根路径的第二个彩蛋")
    @RequestMapping(value = "/egg/{name}", method = RequestMethod.GET)
    public HelloDto anotherEgg(@PathVariable String name) {
        return HelloDto.builder().serverTime(LocalDateTime.now().toString())
                .utcTime(LocalDateTime.now().toString())
                .apiVersion(apiVersion)
                .message("hello, "+name+", welcome to kugga-server")
                .build();
    }
}
