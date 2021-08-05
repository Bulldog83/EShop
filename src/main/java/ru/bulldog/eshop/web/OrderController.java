package ru.bulldog.eshop.web;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.util.SessionUtil;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

	@GetMapping
	@Transactional
	public List<OrderDTO> getOrders(HttpServletRequest request) {
		return SessionUtil.getSession(request).map(uuid -> orderService.findBySession(uuid).stream()
				.map(ORDER_FACTORY).collect(Collectors.toList())).orElseGet(ArrayList::new);
	}

	@GetMapping("/{id}")
	public OrderDTO getOrder(@PathVariable long id) {
		CsrfToken token;
		Order order = orderService.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found, id: " + id));
		return ORDER_FACTORY.apply(order);
	}

	@PostMapping
	public void createOrder(HttpServletRequest request) {
		SessionUtil.getSession(request).ifPresent(session -> {
			CartDTO cart = cartService.getCart(session);
			if (!cart.isEmpty()) {
				orderService.create(cart);
				cart.clear();
			}
		});
	}
}
