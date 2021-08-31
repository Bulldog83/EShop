package ru.bulldog.eshop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableEurekaClient
@SpringBootApplication
@EnableAspectJAutoProxy
public class EShop {
	public static void main(String[] args) {
		SpringApplication.run(EShop.class, args);
	}
}
