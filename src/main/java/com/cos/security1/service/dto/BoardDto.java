package com.cos.security1.service.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("BoardDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class BoardDto {
	//보드게시판
	private String board_no;
	private String board_title;	
	private String board_content;
	private String board_createtime;
	private String board_updatetime;
	private String board_imageFileName;
	private String board_writer;
	private String board_tags;
	private String id;
	
	//base64로 인코딩 후 리스트로 보내자!
	private String[] base64BoardImages;  
	//태그들도 구분자 나눈 후 리스트로 보내기!
	private String[] boardTags;
	
	//작성자 프로필 이미지
	private String profileimage;
	
	//게시판 이미지
	private String boardImage_FileName;
	private String boardImage_Size;
	private String boardImage_Route;
	
	//게시판 댓글 수 
	private String comment_count;
	//게시판 좋아요 
	private String like_count;
	private List<LikeDto> likeList;
	private boolean like;
	
	//게시판 위치정보
	private LocationDto locationList;
}
