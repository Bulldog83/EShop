package ru.bulldog.eshop.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EShopDiscoveryService {
	public static void main(String[] args) {
		SpringApplication.run(EShopDiscoveryService.class, args);
	}
}
