package ru.bulldog.eshop.service;

import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.model.Order;

import java.util.Optional;

public interface OrderService {
	Optional<Order> findById(long id);
	Order create(CartDTO cart);
	Order save(Order order);
}
