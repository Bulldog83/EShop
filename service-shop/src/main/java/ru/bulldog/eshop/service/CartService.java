package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Cart;

import java.util.*;
import java.util.function.Consumer;

import static ru.bulldog.eshop.util.EntityConverter.PRODUCT_TO_DTO_FACTORY;

@Service
public class CartService {

	private static final String CART_PREFIX = "CART_";

	private final RedisTemplate<String, Object> redisTemplate;
	private final ProductService productService;

	@Autowired
	public CartService(RedisTemplate<String, Object> redisTemplate, ProductService productService) {
		this.redisTemplate = redisTemplate;
		this.productService = productService;
	}

	public Cart getCart(UUID session) {
		String cartKey = makeKey(session);
		if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
			return (Cart) redisTemplate.opsForValue().get(cartKey);
		}
		Cart cart = new Cart(session);
		redisTemplate.opsForValue().set(cartKey, cart);

		return cart;
	}

	public void addToCart(UUID session, long itemId) {
		updateCart(session, cart -> {
			if (!cart.addItem(itemId)) {
				productService.getById(itemId).ifPresent(product ->
						cart.addItem(PRODUCT_TO_DTO_FACTORY.apply(product)));
			}
		});
	}

	public void removeFromCart(UUID session, long itemId) {
		updateCart(session, cart -> cart.removeItem(itemId));
	}

	public void deleteFromCart(UUID session, long itemId) {
		updateCart(session, cart -> cart.deleteItem(itemId));
	}

	public void mergeCarts(UUID session, Cart otherCart) {
		updateCart(session, cart -> {
			cart.merge(otherCart);
			removeCart(otherCart.getSession());
		});
	}

	public void clearCart(UUID session) {
		updateCart(session, Cart::clear);
	}

	private void updateCart(UUID session, Consumer<Cart> action) {
		Cart cart = getCart(session);
		action.accept(cart);
		redisTemplate.opsForValue().set(makeKey(session), cart);
	}

	private void removeCart(UUID session) {
		String cartKey = makeKey(session);
		if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
			redisTemplate.delete(cartKey);
		}
	}

	private String makeKey(UUID session) {
		return CART_PREFIX + session;
	}
}
