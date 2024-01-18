//package com.cos.security1.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.cos.security1.fillter.MyFillter1;
//
//@Configuration
//public class FilterConfig {
//	
//	@Bean
//	public FilterRegistrationBean<MyFillter1> filter1(){
//		FilterRegistrationBean<MyFillter1> bean = new FilterRegistrationBean<>(new MyFillter1());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(0); // 낮은 번호가 필터중 가장먼저 실행
//		return bean;
//	}
//	
//}
