package com.ayang818.kugga.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 杨丰畅
 */
@MapperScan(basePackages = {"com.ayang818.kugga.services.mapper"})
@SpringBootApplication(scanBasePackages = "com.ayang818.kugga")
public class KuggaStarterApplication {

    public static void main(String[] args) {
        new EnbededKotlin().main();
        SpringApplication.run(KuggaStarterApplication.class, args);
    }

}