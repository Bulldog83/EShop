package ru.bulldog.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderItem;
import ru.bulldog.eshop.repository.OrderRepo;
import ru.bulldog.eshop.service.OrderService;

import java.util.Optional;
import java.util.stream.Collectors;

import static ru.bulldog.eshop.util.EntityUtil.ORDER_ITEM_FACTORY;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepo orderRepo;

	@Autowired
	public OrderServiceImpl(OrderRepo orderRepo) {
		this.orderRepo = orderRepo;
	}

	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}

	@Override
	public Optional<Order> findById(long id) {
		return orderRepo.findById(id);
	}

	@Override
	public Order create(CartDTO cart) {
		Order order = new Order();
		order.setSessionId(cart.getSession());
		order.setSumTotal(cart.getSumTotal());
		order.setItems(cart.getItems().stream()
				.map(ORDER_ITEM_FACTORY)
				.peek(item -> {
					item.setOrder(order);
					item.setId(null);
				})
				.collect(Collectors.toList()));
		return orderRepo.save(order);
	}
}
