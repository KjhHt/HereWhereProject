package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("LikeDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class LikeDto {
	private String like_no;
	private String like_id;
	private String like_createtime;
	private String board_no;
	
	private String profileimage;
	private String name;
}
