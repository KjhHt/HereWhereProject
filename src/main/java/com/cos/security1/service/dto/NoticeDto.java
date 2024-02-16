package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("NoticeDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class NoticeDto {
	private String notice_no;
	private String notice_content;
	private String notice_createtime;
    private String id;
}
