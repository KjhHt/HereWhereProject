package com.cos.security1.service.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("ChatDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class ChatDto {
	private String dm_content;
	
	//채팅 데이타
	private String dm_no;
	private String dm_sender_id;
	private String dm_recipient_id;
	private String dm_sendtime;
	private String dm_type;

	//소켓 아이디 설정
	private String dm_chat_id;
	
}
