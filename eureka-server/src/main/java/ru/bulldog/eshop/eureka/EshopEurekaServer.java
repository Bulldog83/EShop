package ru.bulldog.eshop.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EshopEurekaServer {
	public static void main(String[] args) {
		SpringApplication.run(EshopEurekaServer.class, args);
	}
}
