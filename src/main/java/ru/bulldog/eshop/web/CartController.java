package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.service.CartService;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@GetMapping
	public CartDTO getCart(@RequestBody(required = false) UUID session) {
		if (session == null) {
			session = UUID.randomUUID();
		}
		return cartService.getCart(session);
	}
}
