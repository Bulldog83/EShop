package ru.bulldog.eshop.dto;

import java.math.BigDecimal;

public class CartItemDTO {

	private Long id;
	private String title;
	private BigDecimal price;
	private BigDecimal sum;
	private int count;

	public CartItemDTO() {
		this.count = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		recalculate();
	}

	public BigDecimal getSum() {
		return sum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		recalculate();
	}

	public void addCount(int value) {
		count += value;
		recalculate();
	}

	public void increment() {
		addCount(1);
	}

	public void decrement() {
		addCount(-1);
	}

	private void recalculate() {
		this.sum = price.multiply(BigDecimal.valueOf(count));
	}
}
