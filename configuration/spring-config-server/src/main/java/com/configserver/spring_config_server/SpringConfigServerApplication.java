package com.configserver.spring_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

//@EnableConfigServer -> It will enable the feature of spring config server
@SpringBootApplication
@EnableConfigServer
public class SpringConfigServerApplication {

	public static void main(String[] args) {
		System.out.println("Start Spring Cloud Configuration Server");
		SpringApplication.run(SpringConfigServerApplication.class, args);
	}

}
