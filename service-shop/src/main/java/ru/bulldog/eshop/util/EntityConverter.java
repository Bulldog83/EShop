package ru.bulldog.eshop.util;

import org.springframework.security.core.GrantedAuthority;
import ru.bulldog.eshop.dto.*;
import ru.bulldog.eshop.model.*;

import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityConverter {

	public final static Function<Product, ProductDTO> PRODUCT_TO_DTO_FACTORY = product -> {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setTitle(product.getTitle());
		productDTO.setCategory(product.getCategory().getTitle());
		productDTO.setPrice(product.getPrice());
		productDTO.setPictures(product.getPictures().stream()
				.map(Picture::getSource).collect(Collectors.toList()));

		return productDTO;
	};

	public final static Function<Category, CategoryDTO> CATEGORY_TO_DTO_FACTORY = category -> {
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

	public final static Function<OrderItem, OrderItemDTO> ORDER_ITEM_TO_DTO_FACTORY = orderItem -> {
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setId(orderItem.getId());
		orderItemDTO.setTitle(orderItem.getTitle());
		orderItemDTO.setCount(orderItem.getCount());
		orderItemDTO.setPrice(orderItem.getPrice());
		orderItemDTO.setSum(orderItem.getSum());

		return orderItemDTO;
	};

	public final static Function<Address, AddressDTO> ADDRESS_TO_DTO_FACTORY = address -> {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address.getId());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setZipCode(address.getZipCode());
		addressDTO.setRegion(address.getRegion());
		addressDTO.setCity(address.getCity());
		addressDTO.setStreet(address.getStreet());
		addressDTO.setHouse(address.getHouse());
		addressDTO.setBuilding(address.getBuilding());
		addressDTO.setApartment(address.getApartment());

		return addressDTO;
	};

	public final static Function<AddressDTO, Address> ADDRESS_FACTORY = addressDTO -> {
		Address address = new Address();
		address.setId(addressDTO.getId());
		address.setCountry(addressDTO.getCountry());
		address.setZipCode(addressDTO.getZipCode());
		address.setRegion(addressDTO.getRegion());
		address.setCity(addressDTO.getCity());
		address.setStreet(addressDTO.getStreet());
		address.setHouse(addressDTO.getHouse());
		address.setBuilding(addressDTO.getBuilding());
		address.setApartment(addressDTO.getApartment());

		return address;
	};

	public final static Function<Order, OrderDTO> ORDER_TO_DTO_FACTORY = order -> {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setSessionId(order.getSessionId());
		orderDTO.setAddress(ADDRESS_TO_DTO_FACTORY.apply(order.getAddress()));
		orderDTO.setPhone(order.getPhone());
		orderDTO.setSumTotal(order.getSumTotal());
		orderDTO.setCreated(order.getCreated());
		orderDTO.setItems(order.getItems().stream().map(ORDER_ITEM_TO_DTO_FACTORY).collect(Collectors.toList()));
		orderDTO.setStatus(OrderStatus.of(order.getStatus()).getStatus());

		return orderDTO;
	};

	public final static Function<OrderDTO, Order> ORDER_FACTORY = orderDTO -> {
		Order order = new Order();
		order.setId(orderDTO.getId());
		order.setSessionId(orderDTO.getSessionId());
		order.setAddress(ADDRESS_FACTORY.apply(orderDTO.getAddress()));
		order.setPhone(orderDTO.getPhone());
		order.setSumTotal(orderDTO.getSumTotal());
		order.setCreated(orderDTO.getCreated());
		order.setItems(orderDTO.getItems().stream().map(ORDER_ITEM_FACTORY).collect(Collectors.toList()));
		order.setStatus(OrderStatus.of(orderDTO.getStatus()).getIndex());

		return order;
	};

	public final static Function<User, UserDTO> USER_TO_DTO_FACTORY = user -> {
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

	public final static Function<UserDTO, User> USER_FACTORY = userDTO -> {
		User user = new User();
		user.setId(userDTO.getId());
		user.setSessionId(userDTO.getSessionId());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		return user;
	};
}
