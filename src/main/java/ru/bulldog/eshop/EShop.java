package ru.bulldog.eshop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.bulldog.eshop")
public class EShop {

	public static void main(String[] args) {
		SpringApplication.run(EShop.class, args);
	}
}
