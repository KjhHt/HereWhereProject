package com.cos.security1.config.jwt;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	private MemberService service;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,MemberService service) {
		super(authenticationManager);
		this.service = service;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Cookie[] cookies = request.getCookies();
		String jwttoken = null;
		if(cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("User-Token")) { 
		        	jwttoken = cookie.getValue();
		            break;
		        }
		    }
		}
		
		if(jwttoken == null ) {
			System.out.println("해당 쿠키가 없다");
			chain.doFilter(request, response);
			return;
		}
		
		JWTTokens tokens = new JWTTokens();
		
		if(tokens.verifyToken(jwttoken)) {
			System.out.println("유효한 토큰이다.");
			Map payloads = tokens.getTokenPayloads(jwttoken);
			String username = (String)payloads.get("username");
			if(username != null) {
				UserDto userEntity = service.findByUsername(username);
				PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
				// Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
				Authentication authentication =
						new UsernamePasswordAuthenticationToken(principalDetails, null,principalDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				chain.doFilter(request, response);
			}
		}
		else {
			System.out.println("유효한 토큰이 아니다.");
			chain.doFilter(request, response);
			return;
		}
		
	}
	
	
	
}
