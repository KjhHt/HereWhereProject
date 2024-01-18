package com.cos.security1.service;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cos.security1.service.dto.UserDto;

@Mapper
public interface MybatisMapper {
	
	String findbyUserPassword(Map map);
	Map<String, Object> getInfo(String id);
	int isPreviousUser(String id);
	
	UserDto findByUsername(String id);
	void joinSocialMember(UserDto dto);
	UserDto findByMemberInfo(String id);
	
}
