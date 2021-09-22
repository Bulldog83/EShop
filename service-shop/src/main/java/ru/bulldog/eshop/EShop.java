package ru.bulldog.eshop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaClient
@SpringBootApplication
@EnableAspectJAutoProxy
@PropertySource("classpath:security.properties")
public class EShop {
	public static void main(String[] args) {
		SpringApplication.run(EShop.class, args);
	}
}
