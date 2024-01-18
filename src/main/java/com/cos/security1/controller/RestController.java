package com.cos.security1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private MemberService service;	
	
	@GetMapping("/test")
	public String test() {
		System.out.println("vue build 12");
		return "test";
	}
	
	@GetMapping("/user/test")
	public String userTest() {
		System.out.println("user에 접근함");
		// 문자 메세지~~
//		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSBOMLEW4XQYZAG", "EBSKN1NKGVNW9QZQQSMQYZMJZEBLDMUG", "http://api.coolsms.co.kr");
//		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
//		Message message = new Message();
//
//		message.setFrom("01097957567");
//		message.setTo("01020542040");
//		message.setText("문자 테스트,,,,,");
//
//		try {
//		  // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
//		  messageService.send(message);
//		} catch (NurigoMessageNotReceivedException exception) {
//		  // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
//		  System.out.println(exception.getFailedMessageList());
//		  System.out.println(exception.getMessage());
//		} catch (Exception e) {
//		  System.out.println(e.getMessage());
//		}
		return "user";
	}
	
	@GetMapping("/manager/test")
	public String managerTest() {
		System.out.println("manager에 접근함");
		return "manager";
	}
	
	@GetMapping("/admin/test")
	public String adminTest() {
		System.out.println("admin에 접근함");
		return "admin";
	}

	// 로그인 후 user권한으로 쿠키에있는 token전달
	@PostMapping("/user/getToken")
	public String getToken(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                System.out.println("쿠키 값: " + cookieValue);
	                return cookieValue;
	            }
	        }
	    }
	    
	    System.out.println("쿠키를 찾을 수 없습니다.");
		return "NoCookie";
	}
	
	// 토큰으로 이름(닉네임),프로필이미지 전달
	@GetMapping("/user/getMemberInfo")
	public UserDto getMemberInfo(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(token);
	    String id = (String)username.get("username");
	    
		UserDto info = service.findByMemberInfo(id);
		return info;
	}
	
	@GetMapping("/isSocialLogin")
	public String isSocialLogin(HttpServletRequest request) {
		
		String token = null;
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                token = cookieValue;
	            }
	        }
	    }
	    
		return token;
	}
	
}
