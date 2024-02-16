package com.cos.security1.service;


import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cos.security1.service.dto.MessagePushDto;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

@Service
public class FCMService {

	private static final Logger logger = LoggerFactory.getLogger(FCMService.class);
	
    public void send(MessagePushDto notificationRequest) throws InterruptedException, ExecutionException, FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(
                        		notificationRequest.getTitle(),
                                notificationRequest.getMessage())
                        		)
                        .build())
                .build();
        System.out.println("token확인 : "+notificationRequest.getToken());
    	
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
    }
	
}
