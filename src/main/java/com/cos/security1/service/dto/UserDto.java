package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("UserDto")
@Getter
@Setter
@AllArgsConstructor // 전체 초기화해주는?
@NoArgsConstructor  // 기본생성자
public class UserDto {
	private String id;
	private String pwd;
	private String authority;
	
	//소셜로그인
	private String name;
	private String profileimage;
	private String provider;

	//일반 회원가입
	private String mbti;
	private String tel;
	private String gender;
	private String address;
	private String lat;
	private String lng;
	
	//프로필 이미지 부분
	private String filename;
	private String filesize;
	private String fileroute;
	
	private String phonenumber;
	
	private String boardcount;
	private String followcount;
	
	
}
