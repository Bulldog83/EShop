package ru.bulldog.eshop.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderStatus;
import ru.bulldog.eshop.service.OrderService;
import ru.bulldog.eshop.util.SessionUtil;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/paypal")
public class PayPalController {

	private final PayPalHttpClient payPalClient;
	private final PayPalService payPalService;
	private final OrderService orderService;

	@Autowired
	public PayPalController(PayPalHttpClient payPalClient,
	                        PayPalService payPalService,
	                        OrderService orderService)
	{
		this.payPalClient = payPalClient;
		this.payPalService = payPalService;
		this.orderService = orderService;
	}

	@PostMapping("/create/{orderId}")
	public ResponseEntity<?> createOrder(HttpServletRequest httpRequest, @PathVariable Long orderId) throws IOException {
		Order order = orderService.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order #" + orderId + " not found."));
		if (order.getStatus() >= OrderStatus.PAID.getIndex()) {
			throw new RequestRejectedException("Order #" + orderId + " already paid.");
		}
		UUID sessionId = SessionUtil.getSession(httpRequest).orElseThrow(() -> new AccessDeniedException("Session not found."));
		if (!sessionId.equals(order.getSessionId())) {
			throw new AccessDeniedException("You don't have permissions for order #" + orderId + " payment.");
		}
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
			orderService.findById(orderId).ifPresent(order -> {
				order.setStatus(OrderStatus.PAID.getIndex());
				orderService.save(order);
			});
			return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
		}
		return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
	}
}
