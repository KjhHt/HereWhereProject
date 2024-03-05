package com.cos.security1.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		
		if( principalDetails.getUsername() == null) {
			System.out.println("소셜 principalDetails.getUsername() 가 null이다");
			return;
		}
		
		Map<String,Object> payloads = new HashMap<>();
		payloads.put("username",principalDetails.getUsername());
		long expirationTime = 1000 * 60 * 60 * 6;//6시간
		JWTTokens tokens = new JWTTokens();
		String token = tokens.createToken(principalDetails.getUsername(), payloads, expirationTime);
		
		Cookie cookie = new Cookie("User-Token", token);
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int)expirationTime);
		cookie.setPath("/");
		cookie.setSecure(true);
		response.addCookie(cookie);
		response.sendRedirect("/");
	}
	
}
