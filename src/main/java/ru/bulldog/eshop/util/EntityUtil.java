package ru.bulldog.eshop.util;

import org.springframework.security.core.GrantedAuthority;
import ru.bulldog.eshop.dto.*;
import ru.bulldog.eshop.model.*;

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

	public final static Function<OrderItemDTO, OrderItem> ORDER_ITEM_FACTORY = orderItemDTO -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(orderItemDTO.getId());
		orderItem.setOrder(orderItem.getOrder());
		orderItem.setTitle(orderItemDTO.getTitle());
		orderItem.setCount(orderItemDTO.getCount());
		orderItem.setPrice(orderItemDTO.getPrice());
		orderItem.setSum(orderItemDTO.getSum());

		return orderItem;
	};

	public final static Function<OrderItem, OrderItemDTO> ORDER_ITEM_DTO_FACTORY = orderItem -> {
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
		orderDTO.setItems(order.getItems().stream().map(ORDER_ITEM_DTO_FACTORY).collect(Collectors.toList()));

		return orderDTO;
	};

	public final static Function<User, UserDTO> USER_DTO_FACTORY = user -> {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setSessionId(user.getSessionId());
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setAuthorities(user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

		return userDTO;
	};
}
