package ru.bulldog.eshop.web;

import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;

	public OrderController(OrderService orderService, CartService cartService) {
		this.orderService = orderService;
		this.cartService = cartService;
	}

	@PostMapping
	public void createOrder(@RequestParam UUID session) {
		CartDTO cart = cartService.getCart(session);
		orderService.create(cart);
		cart.clear();
	}
}
