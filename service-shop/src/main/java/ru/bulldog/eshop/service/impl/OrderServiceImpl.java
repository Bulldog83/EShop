package ru.bulldog.eshop.service.impl;

import ru.bulldog.eshop.model.Address;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderStatus;
import ru.bulldog.eshop.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.service.AddressService;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.util.EntityConverter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepo orderRepo;
	private final AddressService addressService;

	@Autowired
	public OrderServiceImpl(OrderRepo orderRepo, AddressService addressService) {
		this.orderRepo = orderRepo;
		this.addressService = addressService;
	}

	@Override
	public Order save(Order order) {
		return orderRepo.save(order);
	}

	@Override
	public List<Order> findBySession(UUID uuid) {
		return orderRepo.findBySessionId(uuid);
	}

	@Override
	public Optional<Order> findById(long id) {
		return orderRepo.findById(id);
	}

	@Override
	public Order create(OrderDTO orderDTO) {
		orderDTO.setStatus(OrderStatus.NEW.getStatus());
		Order order = EntityConverter.ORDER_FACTORY.apply(orderDTO);
		order.getItems().forEach(item -> {
			item.setOrder(order);
			item.setId(null);
		});
		Address address = order.getAddress();
		if (address.getId() == null) {
			order.setAddress(addressService.create(address));
		}
		return orderRepo.save(order);
	}
}
