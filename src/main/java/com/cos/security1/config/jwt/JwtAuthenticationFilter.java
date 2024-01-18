package com.cos.security1.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}
	
	// login 요청하면 실행됨
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(id,pwd);

		System.out.println("attemptAuthentication 실행  ");
		Authentication authenticated = authenticationManager.authenticate(authenticationToken);

		PrincipalDetails principalDetails = (PrincipalDetails) authenticated.getPrincipal();
		System.out.println(principalDetails.getUsername());
		
		return authenticated;
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
			PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
			System.out.println("principalDetails :"+principalDetails);
			
			Map<String,Object> payloads = new HashMap<>();
			payloads.put("username",principalDetails.getUsername());
			long expirationTime = 1000 * 60 * 60 * 1;//1시간
			
			JWTTokens tokens = new JWTTokens();
			String token = tokens.createToken(principalDetails.getUsername(), payloads, expirationTime);
		
			Cookie cookie = new Cookie("User-Token", token);
			cookie.setHttpOnly(true);
			cookie.setMaxAge((int)expirationTime);
			cookie.setPath("/");
			cookie.setSecure(true);
			response.addCookie(cookie);
			
			//response.addHeader("Authorization", "Bearer "+token);
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
}
