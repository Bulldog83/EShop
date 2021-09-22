package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.CartDTO;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class CartService {

	private static final String CART_PREFIX = "CART_";

	private final RedisTemplate<String, Object> redisTemplate;

	@Autowired
	public CartService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
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

	public void mergeCarts(UUID session, CartDTO cart) {
		CartDTO cartDTO = getCart(session);
		cartDTO.merge(cart);
		updateCart(session, cartDTO);
	}

	public void clearCart(UUID session) {
		CartDTO cartDTO = getCart(session);
		cartDTO.clear();
		updateCart(session, cartDTO);
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
