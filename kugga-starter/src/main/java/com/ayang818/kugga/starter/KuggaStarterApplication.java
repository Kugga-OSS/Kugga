package com.ayang818.kugga.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.ayang818.kugga.services.mapper"})
@SpringBootApplication(scanBasePackages = "com.ayang818.kugga")
public class KuggaStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuggaStarterApplication.class, args);
    }

}