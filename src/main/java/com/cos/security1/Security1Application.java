package com.cos.security1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Security1Application {

	public static void main(String[] args) {
		System.out.println("내가 두번째 계정에서 똑같은곳에 작업하고 커밋하려고할때");
		System.out.println("어떻게 되는지");
		System.out.println("테스트를 해보자");
		SpringApplication.run(Security1Application.class, args);
	}

}
