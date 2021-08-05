package ru.bulldog.eshop.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CartDTO {

	private UUID session;
	private List<OrderItemDTO> items;
	private BigDecimal sumTotal;

	public CartDTO() {
		this.items = new ArrayList<>();
		this.sumTotal = BigDecimal.ZERO;
	}

	public CartDTO(UUID session) {
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

	public void clear() {
		sumTotal = BigDecimal.ZERO;
		items.clear();
	}

	public boolean isEmpty() {
		return items.isEmpty() && sumTotal.equals(BigDecimal.ZERO);
	}

	private void recalculate() {
		sumTotal = BigDecimal.ZERO;
		items.forEach(item -> sumTotal = sumTotal.add(item.getSum()));
	}
}
