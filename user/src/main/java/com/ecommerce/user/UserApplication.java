package com.ecommerce.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		System.out.println("Hi This is User Service");
		SpringApplication.run(UserApplication.class, args);
		System.out.println("Hi This is User Service");
	}
}
