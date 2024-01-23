package com.cos.security1.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.UserDto;
import com.cos.security1.util.FileUtils;
import com.cos.security1.util.JWTTokens;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

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
	
	//회원가입
    @PostMapping("/joinMember")
    public String joinMember(
            @RequestPart("id") String id,
            @RequestPart("pwd") String pwd,
            @RequestPart("name") String name,
            @RequestPart("gender") String gender,
            @RequestPart("mbti") String mbti,
            @RequestPart("tel") String tel,
            @RequestPart("address") String address,
            @RequestPart("profileImage") MultipartFile profileImage,
            HttpServletRequest req
    ) throws IOException, ServletException {
        // 사용자 데이터 및 프로필 이미지 파일을 받아와 처리하는 로직을 작성
        System.out.println("ID: " + id);
        System.out.println("Profile Image File NAME: " + profileImage.getOriginalFilename());//파일이름
        System.out.println("Profile Image File SIZE: " + profileImage.getSize());//파일이름
        long size = profileImage.getSize();
        String filesize = Long.toString(size);
        // 파일 미지정 시 회원가입페이지에서 디폴트 파일객체 보내주도록 설정!!
        String saveDirectory = req.getServletContext().getRealPath("/upload");
        System.out.println(saveDirectory);
		Collection<Part> parts = req.getParts();
		StringBuffer filenames = FileUtils.upload(parts, saveDirectory);
		
		
		System.out.println("잠시 테스트");
		
		
		UserDto dto = new UserDto();
		
		//수정해야함
		dto.setLat("123");
		dto.setLng("456");
		
		
		dto.setId(id); dto.setPwd(pwd); dto.setName(name); 
		dto.setGender( gender.substring(0, 1).toUpperCase() ); 
		dto.setMbti(mbti);
		dto.setTel(tel); dto.setAddress(address); dto.setProfileimage(saveDirectory+"\\"+filenames);
		dto.setFilename(filenames.toString()); dto.setFileroute(saveDirectory+"\\"+filenames);
		dto.setFilesize(filesize); dto.setAuthority("ROLE_USER"); dto.setProvider("WIT");
		

		
		service.joinMember(dto);
		
		
        return "회원가입 성공";
    }
	
	
	
}
