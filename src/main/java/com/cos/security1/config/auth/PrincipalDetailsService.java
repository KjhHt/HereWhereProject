package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.service.MemberService;
import com.cos.security1.service.dto.UserDto;

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private MemberService service;
	
	// 시큐리티 session(내부 Authentication(내부 UserDetails))
	// 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username : "+username);
		UserDto userEntity = service.findByUsername(username);
		if(userEntity != null) {
			System.out.println("loadUserByUsername 실행!! 아이디찾음 ");
			return new PrincipalDetails(userEntity);
		}
		return null;
	}
	
}
