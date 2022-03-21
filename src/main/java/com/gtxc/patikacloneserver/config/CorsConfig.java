//package com.gtxc.patikacloneserver.config;
//
///*
//    Created by gt at 1:31 PM on Monday, March 07, 2022.
//    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.config.
//*/
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry
//                        .addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("*")
////                        .allowedOrigins("http://localhost:3000");
//                ;
//            }
//        };
//    }
//}
