package ru.bulldog.eshop.web;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.errors.EntityValidationException;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.User;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.service.UserService;
import ru.bulldog.eshop.util.SessionUtil;
import ru.bulldog.eshop.util.Validator;
import ru.bulldog.eshop.util.Validator.ValidationResult;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.bulldog.eshop.util.EntityConverter.ORDER_TO_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;
	private final UserService userService;
	private final CartService cartService;

	public OrderController(OrderService orderService, UserService userService, CartService cartService) {
		this.orderService = orderService;
		this.userService = userService;
		this.cartService = cartService;
	}

	@GetMapping
	public List<OrderDTO> getOrders(HttpServletRequest request) {
		return SessionUtil.getSession(request).map(uuid -> orderService.findBySession(uuid).stream()
				.map(ORDER_TO_DTO_FACTORY).collect(Collectors.toList())).orElseGet(ArrayList::new);
	}

	@GetMapping("/{id}")
	public OrderDTO getOrder(HttpServletRequest request, @PathVariable long id) {
		UUID sessionId = SessionUtil.getSession(request).orElseThrow(() -> new AccessDeniedException("Session not found."));
		Order order = orderService.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found, id: " + id));
		if (sessionId.equals(order.getSessionId())) {
			return ORDER_TO_DTO_FACTORY.apply(order);
		} else {
			Optional<User> user = userService.findBySessionId(sessionId);
			if (user.isPresent() && user.get().getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.anyMatch(authority -> authority.equals("ALL") || authority.equals("MANAGE_ORDERS")))
			{
				return ORDER_TO_DTO_FACTORY.apply(order);
			}
		}
		throw new AccessDeniedException("You haven't permissions to view order #" + id);
	}

	@PostMapping
	public OrderDTO createOrder(HttpServletRequest request, @RequestBody OrderDTO orderDTO) {
		ValidationResult validationResult = Validator.validate(orderDTO);
		if (validationResult.hasErrors()) {
			throw new EntityValidationException(validationResult.getErrors());
		}
		Order order = orderService.create(orderDTO);
		cartService.clearCart(order.getSessionId());
		return ORDER_TO_DTO_FACTORY.apply(order);
	}
}
