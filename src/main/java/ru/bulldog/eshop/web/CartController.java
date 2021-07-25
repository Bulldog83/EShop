package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.ProductService;
import ru.bulldog.eshop.util.EntityUtil;

import java.util.UUID;

import static ru.bulldog.eshop.util.EntityUtil.PRODUCT_FACTORY;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

	private final CartService cartService;
	private final ProductService productService;

	@Autowired
	public CartController(CartService cartService, ProductService productService) {
		this.cartService = cartService;
		this.productService = productService;
	}

	@GetMapping
	public CartDTO getCart(@RequestParam(required = false) UUID session) {
		if (session == null) {
			session = UUID.randomUUID();
		}
		return cartService.getCart(session);
	}

	@PutMapping("/add/{id}")
	public void addItem(@RequestParam UUID session, @PathVariable long id) {
		CartDTO cart = cartService.getCart(session);
		if (!cart.addItem(id)) {
			productService.getById(id).ifPresent(product -> cart.addItem(PRODUCT_FACTORY.apply(product)));
		}
	}

	@PutMapping("/remove/{id}")
	public void removeItem(@RequestParam UUID session, @PathVariable long id) {
		CartDTO cart = cartService.getCart(session);
		cart.removeItem(id);
	}

	@DeleteMapping("/{id}")
	public void deleteItem(@RequestParam UUID session, @PathVariable long id) {
		CartDTO cart = cartService.getCart(session);
		cart.deleteItem(id);
	}
}
