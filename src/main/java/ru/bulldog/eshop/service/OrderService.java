package ru.bulldog.eshop.service;

import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
	Optional<Order> findById(long id);
	Order create(CartDTO cart);
	Order save(Order order);
	List<Order> findBySession(UUID uuid);
}
