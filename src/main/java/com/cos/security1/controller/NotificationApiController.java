package com.cos.security1.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.security1.service.FCMService;
import com.cos.security1.service.MemberService;
import com.cos.security1.service.NotificationService;
import com.cos.security1.service.dto.MessagePushDto;
import com.cos.security1.util.JWTTokens;
import com.google.firebase.messaging.FirebaseMessagingException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class NotificationApiController {
	
    @Autowired
    private FCMService fcmService;
	@Autowired
	private MemberService service;	
	
    private final NotificationService notificationService;

    public NotificationApiController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    
    @PostMapping("/register")
    public ResponseEntity register(
    		@RequestBody Map<String, String> request,
    		HttpServletRequest req) {
        
    	String value = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                value = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(value);
	    String id = (String)username.get("username");
    	
    	
    	String token = request.get("token");
    	//System.out.println("token : " +token );
        //System.out.println("id : "+id);
    	notificationService.register(id, token);
    	//System.out.println("register 완료");
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/pushSend")
    public ResponseEntity<?> sendNotification(@RequestBody MessagePushDto request) throws FirebaseMessagingException {
        try {
        	System.out.println(request.getId());
            // 사용자의 푸시 토큰을 데이터베이스에서 불러옵니다.
            String token = service.findTokenByUserId(request.getId());
            System.out.println("메세지 보낼때");
            // 푸시 알림을 보내기 위한 정보를 설정합니다.
            request.setToken(token);
            // 푸시 알림을 보냅니다.
            fcmService.send(request);
            System.out.println("보내기");
            return ResponseEntity.ok().build();
        } catch (InterruptedException | ExecutionException e) {
        	 System.out.println("에러");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("deletePushToken")
    public ResponseEntity deletePushToken(HttpServletRequest req) {
    	String value = null;
		Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("User-Token".equals(cookie.getName())) {  // 쿠키의 이름이 "User-Token"인 경우
	                String cookieValue = cookie.getValue();
	                value = cookieValue;
	            }
	        }
	    }
	    Map username = JWTTokens.getTokenPayloads(value);
	    String id = (String)username.get("username");
	    
	    if(id!=null)
	    	service.deletePushToken(id);
	    
    	return ResponseEntity.ok().build();
    }
    
    
    
    
    
}
