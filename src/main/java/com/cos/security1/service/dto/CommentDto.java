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
}