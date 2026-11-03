package com.careercopilot.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {
	//*****************WHILE USE EUREKA REMOVE THAT FALSE FROM application.properties*************//
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
