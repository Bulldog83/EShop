package ru.bulldog.eshop.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/paypal")
public class PayPalController {

	private final PayPalHttpClient payPalClient;
	private final OrderService orderService;

	@Autowired
	public PayPalController(PayPalHttpClient payPalClient, OrderService orderService) {
		this.payPalClient = payPalClient;
		this.orderService = orderService;
	}

	@PostMapping("/create/{orderId}")
	public ResponseEntity<?> createOrder(@PathVariable Long orderId) throws IOException {
		OrdersCreateRequest request = new OrdersCreateRequest();
		request.prefer("return=representation");
		request.requestBody(createOrderRequest(orderId));

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
			return new ResponseEntity<>("Order completed!", HttpStatus.valueOf(response.statusCode()));
		}
		return new ResponseEntity<>(payPalOrder, HttpStatus.valueOf(response.statusCode()));
	}

	private OrderRequest createOrderRequest(Long orderId) {
		Optional<Order> orderOptional = orderService.findById(orderId);
		if (orderOptional.isPresent()) {
			Order order = orderOptional.get();
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.checkoutPaymentIntent("CAPTURE");

			ApplicationContext applicationContext = new ApplicationContext().brandName("E-Stationary").landingPage("BILLING")
					.shippingPreference("SET_PROVIDED_ADDRESS");
			orderRequest.applicationContext(applicationContext);

			BigDecimal sumTotal = order.getSumTotal();
			List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
			PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
					.referenceId(orderId.toString())
					.description("Stationary Goods")
					.amountWithBreakdown(new AmountWithBreakdown().currencyCode("RUB").value(sumTotal.toString())
							.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("RUB").value(sumTotal.toString()))))
					.items(order.getItems().stream()
							.map(orderItem ->
									new Item()
											.name(orderItem.getTitle())
											.unitAmount(new Money().currencyCode("RUB").value(orderItem.getPrice().toString()))
											.quantity(String.valueOf(orderItem.getCount())))
							.collect(Collectors.toList()))
					.shippingDetail(new ShippingDetail().name(new Name().fullName("Aleksey Seregin"))
							.addressPortable(new AddressPortable().addressLine1("123 Townsend St").addressLine2("Floor 6")
									.adminArea2("San Francisco").adminArea1("CA").postalCode("94107").countryCode("US")));
			purchaseUnitRequests.add(purchaseUnitRequest);
			orderRequest.purchaseUnits(purchaseUnitRequests);
			return orderRequest;
		}
		throw new EntityNotFoundException("Order not found: " + orderId);
	}
}
