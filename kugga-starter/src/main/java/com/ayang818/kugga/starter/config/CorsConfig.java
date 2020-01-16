// package com.ayang818.kugga.starter.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
//
// /**
//  * @author 杨丰畅
//  * @description TODO
//  * @date 2020/1/16 12:51
//  **/
// @Configuration
// public class CorsConfig {
//
//     @Bean
//     public CorsFilter corsFilter() {
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         CorsConfiguration config = new CorsConfiguration();
//         config.setAllowCredentials(true);
//         config.addAllowedOrigin("*");
//         config.addAllowedMethod("*");
//         config.addAllowedHeader("*");
//
//         source.registerCorsConfiguration("*", config);
//         return new CorsFilter(source);
//     }
// }
