package ru.bulldog.eshop.service;

import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.model.Order;

public interface OrderService {
	Order create(CartDTO cart);
	Order save(Order order);
}
