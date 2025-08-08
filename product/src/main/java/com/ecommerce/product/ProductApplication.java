package com.ecommerce.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {
	public static void main(String[] args) {
		System.out.println("This is Product Service");
		SpringApplication.run(ProductApplication.class, args);
		System.out.println("This is Product Service");
	}
}
