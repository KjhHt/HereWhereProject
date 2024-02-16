package com.cos.security1.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Service
public class FCMInitializer {
	private static final Logger logger = LoggerFactory.getLogger(FCMInitializer.class);
	private static final String FIREBASE_CONFIG_PATH = "herewhere-b85b9-894f33b4cff1.json";

	@PostConstruct
	public void initialize() {
	    try {
	        FirebaseOptions options = new FirebaseOptions.Builder()
	                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
	        if (FirebaseApp.getApps().isEmpty()) {
	        	System.out.println("파이어베이스 연결(초기화)");
	            FirebaseApp.initializeApp(options);
	            logger.info("Firebase application has been initialized");
	        }
	    } catch (IOException e) {
	    	System.out.println("파이어베이스 오류");
	        logger.error(e.getMessage());
	    }
	}
}
