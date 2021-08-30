package ru.bulldog.eshop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableEurekaServer
@SpringBootApplication
@EnableAspectJAutoProxy
public class EShop {
	public static void main(String[] args) {
		SpringApplication.run(EShop.class, args);
	}
}
