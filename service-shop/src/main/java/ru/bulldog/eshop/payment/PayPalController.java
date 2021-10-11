package ru.bulldog.eshop.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderStatus;
import ru.bulldog.eshop.model.User;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/paypal")
public class PayPalController {

	private final PayPalHttpClient payPalClient;
	private final PayPalService payPalService;
	private final OrderService orderService;
	private final UserService userService;

	@Autowired
	public PayPalController(PayPalHttpClient payPalClient,
	                        PayPalService payPalService,
	                        OrderService orderService,
	                        UserService userService)
	{
		this.payPalClient = payPalClient;
		this.payPalService = payPalService;
		this.orderService = orderService;
		this.userService = userService;
	}

	@PostMapping("/create/{orderId}")
	public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
		Order order = orderService.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order #" + orderId + " not found."));
		if (order.getStatus() >= OrderStatus.PAID.getIndex()) {
			throw new RequestRejectedException("Order #" + orderId + " already paid.");
		}
		userService.findBySessionId(order.getSessionId())
				.orElseThrow(() -> new AccessDeniedException("You don't have permission for payment order #" + orderId));
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.prefer("return=representation");
		request.requestBody(payPalService.createOrderRequest(order));

		HttpResponse<com.paypal.orders.Order> response = payPalClient.execute(request);
		if (response.statusCode() == 201) {
			return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
		}
		return new ResponseEntity<>(HttpStatus.valueOf(response.statusCode()));
	}

	@PostMapping("/capture/{payPalId}")
	public ResponseEntity<?> captureOrder(@PathVariable String payPalId) throws IOException {
		OrdersCaptureRequest request = new OrdersCaptureRequest(payPalId);
		request.requestBody(new OrderRequest());

		HttpResponse<com.paypal.orders.Order> response = payPalClient.execute(request);
		com.paypal.orders.Order payPalOrder = response.result();
		if ("COMPLETED".equals(payPalOrder.status())) {
			long orderId = Long.parseLong(payPalOrder.purchaseUnits().get(0).referenceId());
			Optional<Order> orderOptional = orderService.findById(orderId);
			orderOptional.ifPresent(order -> {
				order.setStatus(OrderStatus.PAID.getIndex());
				orderService.save(order);
			});
			return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
		}
		return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
	}
}
