package ru.bulldog.eshop;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ru.bulldog.eshop")
public class EShopConfig {

	@Bean
	public SessionFactory getSessionFactory() {
		return new org.hibernate.cfg.Configuration()
				.configure("hibernate.cfg.xml")
				.buildSessionFactory();
	}
}
