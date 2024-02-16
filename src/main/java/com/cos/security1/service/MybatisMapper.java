package com.cos.security1.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cos.security1.service.dto.BoardDto;
import com.cos.security1.service.dto.ChatDto;
import com.cos.security1.service.dto.CommentDto;
import com.cos.security1.service.dto.FollowDto;
import com.cos.security1.service.dto.NoticeDto;
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
	//알림
	void pushInsert(String id, String token);
	int pushFindToken(String id);
	void pushUpdate(String id, String token);
	String findTokenByUserId(String id);
	void deletePushToken(String id);
	//팔로우
	List<FollowDto> getFollowList(String id);
	int findById(String id);
	int checkFollowRequest(FollowDto followRequest);
	void insertFollow(FollowDto followRequest);
	//채팅
	ChatDto getDmNo(String id, String rid);
	List<ChatDto> chatList(String dm_no);
	void insertChatRoom(String id, String rid);
	String getSeqDmNo();
	void insertMessage(ChatDto chatdto);
	//알림
	List<NoticeDto> noticeList(String id);
	void insertNotice(NoticeDto dto);
	String getNoticeNo(FollowDto followRequest);
	void successFollow(String notice_no);
	void FailFollow(String notice_no);
	void deleteNotice(String notice_no); 
	
}
