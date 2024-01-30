package com.cos.security1.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.UserDto;

@Mapper
public interface MybatisMapper {
	
	String findbyUserPassword(Map map);
	Map<String, Object> getInfo(String id);
	int isPreviousUser(String id);
	
	UserDto findByUsername(String id);
	void joinSocialMember(UserDto dto);
	UserDto findByMemberInfo(String id);
	
	//회원가입
	void joinMember(UserDto dto);
	void joinMemberInfo(UserDto dto);
	void profileImageTable(UserDto dto);
	void updateRecentLoginTime(String id);
	// 게시판
	List<BoardDto> boardList(String num);
	void boardInsert(BoardDto dto);
	BoardDto findBoardNo(String fileName);
	void boardImageInsert(BoardDto imageDto);
	//댓글
	List<CommentDto> commentList(String board_no);
	void commentInsert(CommentDto commentDto);
	//좋아요
	int islike(String id,String board_no);
	void insertLike(String id, String board_no);
	void deleteLike(String id, String board_no);
	
}
