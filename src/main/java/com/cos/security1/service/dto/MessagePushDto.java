package com.cos.security1.service.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("MessagePushDto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
public class MessagePushDto {
    private String title;
    private String token;
    private String message;
    
    //푸쉬 보낼 아이디 ( 테스트중 나중에 삭제할것 )
    private String id;
}
