package ru.bulldog.eshop.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CartDTO {

	private UUID session;
	private List<CartItemDTO> items;
	private BigDecimal totalSum;

	public CartDTO() {
		this.items = new ArrayList<>();
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

	public List<CartItemDTO> getItems() {
		return items;
	}

	public void setItems(List<CartItemDTO> items) {
		this.items = items;
	}

	public BigDecimal getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(BigDecimal totalSum) {
		this.totalSum = totalSum;
	}

	public boolean addItem(long itemId) {
		Optional<CartItemDTO> cartItem = items.stream().filter(item -> item.getId() == itemId).findFirst();
		if (cartItem.isPresent()) {
			cartItem.get().increment();
			recalculate();
			return true;
		}
		return false;
	}

	public void addItem(ProductDTO productDTO) {
		CartItemDTO cartItem = new CartItemDTO();
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

	public void setCount(long itemId, int count) {
		items.stream().filter(item -> item.getId() == itemId).findFirst().ifPresent(item -> {
			item.setCount(count);
			recalculate();
		});
	}

	public void clear() {
		items.clear();
	}

	private void recalculate() {
		totalSum = BigDecimal.ZERO;
		items.forEach(item -> totalSum = totalSum.add(item.getSum()));
	}
}
