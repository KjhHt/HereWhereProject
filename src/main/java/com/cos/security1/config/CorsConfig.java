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
		config.setAllowCredentials(true); 
		config.addAllowedOrigin("http://localhost:8080");
		config.addAllowedOriginPattern("*"); 
		config.addAllowedHeader("*"); 
		config.addAllowedMethod("*"); 
		source.registerCorsConfiguration("/api/**",config);
		source.registerCorsConfiguration("/oauth2/**",config);
		source.registerCorsConfiguration("/user/**",config);
		source.registerCorsConfiguration("/manager/**",config);
		source.registerCorsConfiguration("/admin/**",config);
		return new CorsFilter(source);
	}
}
