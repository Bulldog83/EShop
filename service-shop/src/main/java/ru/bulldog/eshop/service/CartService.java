package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.CartDTO;

import java.util.*;

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

	public CartDTO getCart(UUID session) {
		String cartKey = CART_PREFIX + session;
		if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
			return (CartDTO) redisTemplate.opsForValue().get(cartKey);
		}
		CartDTO cart = new CartDTO(session);
		updateCart(session, cart);

		return cart;
	}

	public void addToCart(UUID session, long itemId) {
		CartDTO cart = getCart(session);
		if (!cart.addItem(itemId)) {
			productService.getById(itemId).ifPresent(product ->
					cart.addItem(PRODUCT_TO_DTO_FACTORY.apply(product)));
		}
		updateCart(session, cart);
	}

	public void removeFromCart(UUID session, long itemId) {
		CartDTO cart = getCart(session);
		cart.removeItem(itemId);
		updateCart(session, cart);
	}

	public void deleteFromCart(UUID session, long itemId) {
		CartDTO cart = getCart(session);
		cart.deleteItem(itemId);
		updateCart(session, cart);
	}

	public void mergeCarts(UUID session, CartDTO cart) {
		CartDTO cartDTO = getCart(session);
		cartDTO.merge(cart);
		updateCart(session, cartDTO);
		removeCart(cart.getSession());
	}

	public void clearCart(UUID session) {
		CartDTO cartDTO = getCart(session);
		if (!cartDTO.isEmpty()) {
			cartDTO.clear();
			updateCart(session, cartDTO);
		}
	}

	public Optional<CartDTO> removeCart(UUID session) {
		String cartKey = CART_PREFIX + session;
		if (Boolean.TRUE.equals(redisTemplate.hasKey(cartKey))) {
			CartDTO cartDTO = (CartDTO) redisTemplate.opsForValue().get(cartKey);
			redisTemplate.delete(cartKey);
			return Optional.ofNullable(cartDTO);
		}
		return Optional.empty();
	}

	public void updateCart(UUID session, CartDTO cart) {
		redisTemplate.opsForValue().set(CART_PREFIX + session, cart);
	}
}
