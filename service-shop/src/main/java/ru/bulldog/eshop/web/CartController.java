package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.ProductService;
import ru.bulldog.eshop.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

	private final CartService cartService;

	@Autowired
	public CartController(CartService cartService, ProductService productService) {
		this.cartService = cartService;
	}

	@GetMapping
	public CartDTO getCart(HttpServletRequest request) {
		return SessionUtil.getSession(request).map(cartService::getCart)
				.orElseGet(() -> cartService.getCart(UUID.randomUUID()));
	}

	@PutMapping("/add/{id}")
	public void addItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> cartService.addToCart(session, id));
	}

	@PutMapping("/remove/{id}")
	public void removeItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> cartService.removeFromCart(session, id));
	}

	@PutMapping("/delete/{id}")
	public void deleteItem(HttpServletRequest request, @PathVariable long id) {
		SessionUtil.getSession(request).ifPresent(session -> cartService.deleteFromCart(session, id));
	}

	@PutMapping("/merge")
	public void mergeCarts(HttpServletRequest request, @RequestParam("session") UUID oldSession) {
		SessionUtil.getSession(request).ifPresent(session ->
				cartService.removeCart(oldSession).ifPresent(oldCart ->
						cartService.mergeCarts(session, oldCart)));
	}

	@PutMapping("/clear")
	public void clearCart(HttpServletRequest request) {
		SessionUtil.getSession(request).ifPresent(cartService::clearCart);
	}
}
