package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("CommentDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class CommentDto {
	private String comment_no;
	private String comment_writer;
	private String comment_content;
	private String comment_createtime;
	private String board_no;
	
	//작성자 아이디
	private String id;
	//작성자 프로필
	private String profileimage;
	
	//댓글 좋아요 개수 및 여부
	private String like_count;
	private String user_liked;
	
	//댓글 감정분석
	private String comment_result;
	private String comment_probability;
	private String comment_sentiment;
}
