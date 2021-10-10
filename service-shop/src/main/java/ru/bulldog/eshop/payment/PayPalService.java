package ru.bulldog.eshop.payment;

import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Address;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.service.OrderService;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayPalService {

	private final OrderService orderService;

	@Autowired
	public PayPalService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderRequest createOrderRequest(Long orderId) {
		Optional<Order> orderOptional = orderService.findById(orderId);
		if (orderOptional.isPresent()) {
			Order order = orderOptional.get();
			OrderRequest orderRequest = new OrderRequest();
			orderRequest.checkoutPaymentIntent("CAPTURE");

			ApplicationContext applicationContext = new ApplicationContext().brandName("E-Stationary").landingPage("BILLING")
					.shippingPreference("SET_PROVIDED_ADDRESS");
			orderRequest.applicationContext(applicationContext);

			Address address = order.getAddress();
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
							.addressPortable(new AddressPortable().addressLine1(address.getStreet()).addressLine2(address.getHouse())
									.adminArea2(address.getCity()).countryCode(address.getCountry())));
			purchaseUnitRequests.add(purchaseUnitRequest);
			orderRequest.purchaseUnits(purchaseUnitRequests);
			return orderRequest;
		}
		throw new EntityNotFoundException("Order not found: " + orderId);
	}
}
