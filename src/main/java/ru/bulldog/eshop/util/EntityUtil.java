package ru.bulldog.eshop.util;

import ru.bulldog.eshop.dto.CategoryDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.dto.OrderItemDTO;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Category;
import ru.bulldog.eshop.model.Order;
import ru.bulldog.eshop.model.OrderItem;
import ru.bulldog.eshop.model.Product;

import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityUtil {

	public final static Function<Product, ProductDTO> PRODUCT_FACTORY = product -> {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setTitle(product.getTitle());
		productDTO.setCategory(product.getCategory().getTitle());
		productDTO.setPrice(product.getPrice());

		return productDTO;
	};

	public final static Function<Category, CategoryDTO> CATEGORY_FACTORY = category -> {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setTitle(category.getTitle());

		return categoryDTO;
	};

	public final static Function<OrderItem, OrderItemDTO> ORDER_ITEM_FACTORY = orderItem -> {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem.getId());
		orderItemDTO.setTitle(orderItem.getTitle());
		orderItemDTO.setCount(orderItem.getCount());
		orderItemDTO.setPrice(orderItem.getPrice());
		orderItemDTO.setSum(orderItem.getSum());

		return orderItemDTO;
	};

	public final static Function<Order, OrderDTO> ORDER_FACTORY = order -> {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setSessionId(order.getSessionId());
		orderDTO.setSumTotal(order.getSumTotal());
		orderDTO.setCreated(order.getCreated());
		orderDTO.setItems(order.getItems().stream().map(ORDER_ITEM_FACTORY).collect(Collectors.toList()));

		return orderDTO;
	};
}
