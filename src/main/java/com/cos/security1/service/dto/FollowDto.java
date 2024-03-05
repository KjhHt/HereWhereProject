package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("FollowDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class FollowDto {
	private String id;
	private String name;
	private String profileImage;
	
	//알림용
	private String follow_no;
	private String follow_senderid;
	private String follow_recipientid;
	private String follow_requesttime;
	private String follow_is;
	private String follow_responsetime;
	
	private String notice_no;
	
	private String follow_isRequest;
	
}
