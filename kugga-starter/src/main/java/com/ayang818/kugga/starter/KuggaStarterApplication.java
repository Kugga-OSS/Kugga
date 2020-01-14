package com.ayang818.kugga.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ayang818.kugga"})
public class KuggaStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(KuggaStarterApplication.class, args);
    }

}
