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

	
	
}
