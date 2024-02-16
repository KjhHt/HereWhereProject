package com.cos.security1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	private MybatisMapper mapper;
	
    public void register(String id, String token) {
    	int count = mapper.pushFindToken(id);
    	if(count == 0) {
    		mapper.pushInsert(id, token);
    	} else {
    		mapper.pushUpdate(id, token);
    	}
    }
    
}
