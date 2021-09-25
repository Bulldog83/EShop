package ru.bulldog.eshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.bulldog.eshop.dto.OrderItemDTO;
import ru.bulldog.eshop.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {

	private UUID session;
	private List<OrderItemDTO> items;
	private BigDecimal sumTotal;

	public Cart() {
		this.items = new ArrayList<>();
		this.sumTotal = BigDecimal.ZERO;
	}

	public Cart(UUID session) {
		this();
		this.session = session;
	}

	public UUID getSession() {
		return session;
	}

	public void setSession(UUID session) {
		this.session = session;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}

	public BigDecimal getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(BigDecimal sumTotal) {
		this.sumTotal = sumTotal;
	}

	public boolean addItem(long itemId) {
		Optional<OrderItemDTO> cartItem = items.stream().filter(item -> item.getId() == itemId).findFirst();
		if (cartItem.isPresent()) {
			cartItem.get().increment();
			recalculate();
			return true;
		}
		return false;
	}

	public void addItem(ProductDTO productDTO) {
		OrderItemDTO cartItem = new OrderItemDTO();
		cartItem.setId(productDTO.getId());
		cartItem.setTitle(productDTO.getTitle());
		cartItem.setPrice(productDTO.getPrice());
		items.add(cartItem);
		recalculate();
	}

	public void removeItem(long itemId) {
		items.stream().filter(item -> item.getId() == itemId).findFirst().ifPresent(item -> {
			if (item.getCount() > 1) {
				item.decrement();
			} else {
				items.remove(item);
			}
			recalculate();
		});
	}

	public void deleteItem(long itemId) {
		items.stream().filter(item -> item.getId() == itemId).findFirst().ifPresent(item -> {
			items.remove(item);
			recalculate();
		});
	}

	public void setCount(long itemId, int count) {
		items.stream().filter(item -> item.getId() == itemId).findFirst().ifPresent(item -> {
			item.setCount(count);
			recalculate();
		});
	}

	public void merge(Cart oldCart) {
		oldCart.getItems().forEach(item -> {
			int idx = items.indexOf(item);
			if (idx < 0) {
				items.add(item);
			} else {
				items.get(idx).addCount(item.getCount());
			}
		});
		recalculate();
	}

	public void clear() {
		this.sumTotal = BigDecimal.ZERO;
		items.clear();
	}

	public boolean isEmpty() {
		return items.isEmpty() && sumTotal.equals(BigDecimal.ZERO);
	}

	private void recalculate() {
		this.sumTotal = items.stream()
				.map(OrderItemDTO::getSum)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
