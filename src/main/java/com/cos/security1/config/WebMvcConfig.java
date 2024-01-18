package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Configuration
	public class WebMVCConfig implements WebMvcConfigurer{
//		@Override
//		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//			registry.addResourceHandler("/**")
//					.addResourceLocations("classpath:/static/","classpath:/templates/");
//		}

//		@Override
//		public void addViewControllers(ViewControllerRegistry registry) {
//			// TODO Auto-generated method stub
//			WebMvcConfigurer.super.addViewControllers(registry);
//		}
		
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:8080") // Vue.js 서버 주소
	                .allowedMethods("*")
	                .allowedHeaders("*")
	                .allowCredentials(true);
	    }
		
		
		
	}
	
	
	
	
}
