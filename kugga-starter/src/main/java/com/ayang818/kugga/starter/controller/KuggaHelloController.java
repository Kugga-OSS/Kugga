package com.ayang818.kugga.starter.controller;

import com.ayang818.kugga.starter.pojo.HelloDto;
import com.ayang818.kugga.util.GsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 杨丰畅
 * @description Kugga服务的彩蛋Controller
 * @date 2020/1/13 14:09
 **/
@RestController
public class KuggaHelloController {

    @Value("${project.version}")
    String apiVersion;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String egg() {
        HelloDto helloDto = HelloDto.builder().serverTime(LocalDateTime.now().toString()).utcTime(LocalDateTime.now().toString()).apiVersion(apiVersion).message("welcome to kugga-server").build();
        return GsonUtil.toJsonString(helloDto);
    }
}
