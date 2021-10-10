package ru.bulldog.eshop.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderStatus;
import ru.bulldog.eshop.service.OrderService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/paypal")
public class PayPalController {

	private final PayPalHttpClient payPalClient;
	private final PayPalService payPalService;
	private final OrderService orderService;

	@Autowired
	public PayPalController(PayPalHttpClient payPalClient, PayPalService payPalService, OrderService orderService) {
		this.payPalClient = payPalClient;
		this.payPalService = payPalService;
		this.orderService = orderService;
	}

	@PostMapping("/create/{orderId}")
	public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.prefer("return=representation");
		request.requestBody(payPalService.createOrderRequest(orderId));

		HttpResponse<com.paypal.orders.Order> response = payPalClient.execute(request);
		return new ResponseEntity<>(response.result().id(), HttpStatus.valueOf(response.statusCode()));
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
