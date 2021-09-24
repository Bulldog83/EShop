package ru.bulldog.eshop.web;

import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.errors.EntityValidationException;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.util.SessionUtil;
import ru.bulldog.eshop.util.Validator;
import ru.bulldog.eshop.util.Validator.ValidationResult;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bulldog.eshop.util.EntityConverter.ORDER_TO_DTO_FACTORY;

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
	public List<OrderDTO> getOrders(HttpServletRequest request) {
		return SessionUtil.getSession(request).map(uuid -> orderService.findBySession(uuid).stream()
				.map(ORDER_TO_DTO_FACTORY).collect(Collectors.toList())).orElseGet(ArrayList::new);
	}

	@GetMapping("/{id}")
	public OrderDTO getOrder(@PathVariable long id) {
		Order order = orderService.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found, id: " + id));
		return ORDER_TO_DTO_FACTORY.apply(order);
	}

	@PostMapping
	public OrderDTO createOrder(HttpServletRequest request, @RequestBody OrderDTO orderDTO) {
		ValidationResult validationResult = Validator.validate(orderDTO);
		if (validationResult.isValid()) {
			Order order = orderService.create(orderDTO);
			cartService.clearCart(order.getSessionId());
			return ORDER_TO_DTO_FACTORY.apply(order);
		}
		throw new EntityValidationException(validationResult.getErrors());
	}
}
