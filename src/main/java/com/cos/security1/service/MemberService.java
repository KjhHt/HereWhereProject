package com.cos.security1.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.security1.service.dto.UserDto;

@Service
public class MemberService {

	@Autowired
	private MybatisMapper mapper;

    public boolean findbyUserPassword(Map map) {
    	String pwd = mapper.findbyUserPassword(map);
    	if(pwd!=null) {
    	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	    boolean passwordMatch = passwordEncoder.matches(map.get("pwd").toString(), pwd);
    	    return passwordMatch;
    	}
        return false;
    }

	public Map<String, Object> getInfo(String id) {
		return mapper.getInfo(id);
	}

	public boolean isPreviousUser(String id) {
		int ismember = mapper.isPreviousUser(id);
		return ismember > 0;
	}

	public UserDto findByUsername(String id) {
		return mapper.findByUsername(id);
	}

	public void joinSocialMember(UserDto dto) {
		BCryptPasswordEncoder bcy = new BCryptPasswordEncoder();
		String pwd = bcy.encode("소셜로그인");
		dto.setPwd(pwd);
		mapper.joinSocialMember(dto);
	}

	public UserDto findByMemberInfo(String id) {
		return mapper.findByMemberInfo(id);
	}

	//회원가입
	public void joinMember(UserDto dto) {
		// 비밀번호 인코딩
		BCryptPasswordEncoder bcy = new BCryptPasswordEncoder();
		String pwd = bcy.encode(dto.getPwd());
		dto.setPwd(pwd);
		
		// member 테이블
		mapper.joinMember(dto);
		System.out.println("joinMember 완료");
		// member_info 테이블
		mapper.joinMemberInfo(dto);
		System.out.println("joinMemberInfo 완료");
		// profileimage_table 테이블
		mapper.profileImageTable(dto);
		System.out.println("profileImageTable 완료");
		
		
		
		
	}


    

	
}
