package ru.bulldog.eshop.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/paypal")
public class PayPalController {

	@Value("${paypal.clientid}")
	private String clientId;

	@Value("${paypal.secret}")
	private String secret;

	@Value("${paypal.mode}")
	private String mode;
}
