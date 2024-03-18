package com.cos.security1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cos.security1.service.ChatRoomMapper;

@SpringBootApplication
public class Security1Application {
	
    private final ChatRoomMapper chatRoomMapper;

    public Security1Application(ChatRoomMapper chatRoomMapper) {
        this.chatRoomMapper = chatRoomMapper;
    }
    
	public static void main(String[] args) {
		SpringApplication.run(Security1Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
	    return args -> {
	        // 채팅방 인원 수를 0으로 초기화하는 로직
	    	chatRoomMapper.resetUserCount();
	    };
	}
}
