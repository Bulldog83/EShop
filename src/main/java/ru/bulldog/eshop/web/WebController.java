package ru.bulldog.eshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping
	public String getMainPage() {
		return "index";
	}

	@GetMapping("/products")
	public String getProductsPage() {
		return "index";
	}

	@GetMapping("/cart")
	public String getCartPage() {
		return "cart";
	}

	@GetMapping("/orders")
	public String getOrdersPage() {
		return "orders";
	}

	@GetMapping("/orders/new")
	public String getNewOrderPage() {
		return "new_order";
	}

	@GetMapping("/orders/{id}")
	public String getOrderPage() {
		return "order";
	}
}
