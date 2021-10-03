package ru.bulldog.eshop.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class PayPalConfig {

	@Value("${paypal.clientid}")
	private String clientId;

	@Value("${paypal.secret}")
	private String secret;

	private PayPalEnvironment environment;

	@PostConstruct
	private void postInit() {
		this.environment = new PayPalEnvironment.Sandbox(clientId, secret);
	}

	@Bean
	public PayPalHttpClient payPalClient() {
		return new PayPalHttpClient(environment);
	}
}
