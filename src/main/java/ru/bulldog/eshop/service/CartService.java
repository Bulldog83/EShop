package ru.bulldog.eshop.service;

import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.CartDTO;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CartService {

	private Map<UUID, CartDTO> carts;

	public CartService() {}

	@PostConstruct
	private void postInit() {
		this.carts = new HashMap<>();
	}

	public CartDTO getCart(UUID session) {
		if (carts.containsKey(session)) {
			return carts.get(session);
		}
		CartDTO cart = new CartDTO(session);
		carts.put(session, cart);

		return cart;
	}

	public void removeCart(UUID session) {
		carts.remove(session);
	}
}
