package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedOrigins("https://f056c7edebcffb66.ngrok.app") 
                //.allowedOrigins("http://localhost:8080") 
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
//https://herewherhttps://herewhere.come.com