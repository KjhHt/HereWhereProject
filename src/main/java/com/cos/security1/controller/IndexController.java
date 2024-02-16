package com.cos.security1.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.service.MemberService;


@Controller
public class IndexController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MemberService service;	
	
	//@AuthenticationPrincipal 세션정보 접근가능
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(
			Authentication authentication,
			@AuthenticationPrincipal UserDetails userDetails) {
		System.out.println("/test/login ==============");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : "+principalDetails.getUser().getAuthority());
		System.out.println("authentication : "+principalDetails.getUser().getId());
		System.out.println("authentication : "+principalDetails.getUser().getPwd());
		
		System.out.println("userDetails : "+userDetails.getUsername());
		
		return "세션 정보 확인하기";
	}
	
	//@AuthenticationPrincipal 세션정보 접근가능
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOauthLogin(
			Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/oauth/login ==============");
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		
		System.out.println("authentication : "+oauth2User.getAttributes());
		System.out.println("oauth2user : "+oauth.getAttributes() );
		return "세션 정보 확인하기";
	}
	
	
	
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/join")
	public String join(@RequestParam Map map) {
		String id = map.get("id").toString();
		String rawPwd = map.get("pwd").toString();
		String encPwd = bCryptPasswordEncoder.encode(rawPwd);
		String auth = "ROLE_USER";
		System.out.println(encPwd);
		//service.join(id,encPwd,auth);
		
		return "redirect:/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	@GetMapping("/login/oauth2/code/kakao")
	public @ResponseBody String kakao() {
		System.out.println("여기로옴??");
		return "개인정보";
	}
	
	@PostMapping("/token")
	public @ResponseBody String token() {
		return "토큰";
	}
	
	//user권한만 접근가능
	@GetMapping("/api/v1/user")
	public @ResponseBody String user2() {
		System.out.println("난 유저야");
		return "user";
	}
	//manager권한만 접근가능
	@GetMapping("/api/v1/manager")
	public @ResponseBody String manager2() {
		System.out.println("난 manager야");
		return "manager";
	}
	//admin권한만 접근가능
	@GetMapping("/api/v1/admin")
	public @ResponseBody String admin2() {
		System.out.println("난 admin야");
		return "admin";
	}
	
}
