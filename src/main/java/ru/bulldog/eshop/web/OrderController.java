package ru.bulldog.eshop.web;

import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static ru.bulldog.eshop.util.EntityUtil.ORDER_FACTORY;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;

	public OrderController(OrderService orderService, CartService cartService) {
		this.orderService = orderService;
		this.cartService = cartService;
	}

	@GetMapping("/{id}")
	public OrderDTO getOrder(@PathVariable long id) {
		Order order = orderService.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found, id: " + id));
		return ORDER_FACTORY.apply(order);
	}

	@PostMapping
	public void createOrder(@RequestParam UUID session) {
		CartDTO cart = cartService.getCart(session);
		orderService.create(cart);
		cart.clear();
	}
}
