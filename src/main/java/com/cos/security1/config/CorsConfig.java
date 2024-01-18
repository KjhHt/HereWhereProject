package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); //응답할때 json 자바스크립
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedOriginPattern("*"); // 모든 ip
		config.addAllowedHeader("*"); // 모든 header 응답허용
		config.addAllowedMethod("*"); // 모든 post,get....
		source.registerCorsConfiguration("/api/**",config);
		source.registerCorsConfiguration("/oauth2/**",config);
		source.registerCorsConfiguration("/user/**",config);
		source.registerCorsConfiguration("/manager/**",config);
		source.registerCorsConfiguration("/admin/**",config);
		return new CorsFilter(source);
	}
}
