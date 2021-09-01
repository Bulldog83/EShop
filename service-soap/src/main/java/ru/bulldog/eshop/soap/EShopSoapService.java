package ru.bulldog.eshop.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EShopSoapService {
	public static void main(String[] args) {
		SpringApplication.run(EShopSoapService.class, args);
	}
}
