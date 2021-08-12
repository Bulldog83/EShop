package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.ProductService;
import ru.bulldog.eshop.util.EntityUtil;
import ru.bulldog.eshop.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
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
	public CartDTO getCart(HttpServletRequest request) {
		Optional<UUID> sessionOptional = SessionUtil.getSession(request);
		if (sessionOptional.isPresent()) {
			return cartService.getCart(sessionOptional.get());
		}
		return cartService.getCart(UUID.randomUUID());
	}

	@PutMapping("/add/{id}")
	public void addItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> {
			CartDTO cart = cartService.getCart(session);
			if (!cart.addItem(id)) {
				productService.getById(id).ifPresent(product -> cart.addItem(PRODUCT_FACTORY.apply(product)));
			}
		});
	}

	@PutMapping("/remove/{id}")
	public void removeItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> {
			CartDTO cart = cartService.getCart(session);
			cart.removeItem(id);
		});
	}

	@PutMapping("/delete/{id}")
	public void deleteItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> {
			CartDTO cart = cartService.getCart(session);
			cart.deleteItem(id);
		});
	}

	@PutMapping("/merge")
	public void mergeCarts(HttpServletRequest request, @RequestParam("session") UUID oldSession) {
		SessionUtil.getSession(request).ifPresent(session -> {
			CartDTO cart = cartService.getCart(session);
			CartDTO oldCart = cartService.getCart(oldSession);
			cart.merge(oldCart);
		});
	}
}
