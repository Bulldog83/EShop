package ru.bulldog.eshop.model;

import java.util.Objects;

public class Product {

	private Long id;
	private String title;
	private double price;

	public Product() {}

	public Product(String title, double price) {
		this.title = title;
		this.price = price;
	}

	public Product(long id, String title, double price) {
		this.id = id;
		this.title = title;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Product)) return false;
		Product product = (Product) o;
		return id == product.id && title.equals(product.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
