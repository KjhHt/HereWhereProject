package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Security1Application {

	public static void main(String[] args) {
		System.out.println("테스트 같은공간에서 1줄");
		System.out.println("테스트 같은공간에서 2줄");
		System.out.println("여기에다가 수정했을때?");
		System.out.println("테스트 같은공간에서 3줄");
		SpringApplication.run(Security1Application.class, args);
	}

}
