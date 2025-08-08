package com.ecommerce.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		System.out.println("This is Order Service");
		SpringApplication.run(OrderApplication.class, args);
		System.out.println("This is Order Service");
	}
}
