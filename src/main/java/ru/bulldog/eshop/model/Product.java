package ru.bulldog.eshop.model;

import java.util.Objects;

public class Product {

	private long id;
	private String title;
	private double cost;

	public Product() {}

	public Product(String title, double cost) {
		this.title = title;
		this.cost = cost;
	}

	public Product(long id, String title, double cost) {
		this.id = id;
		this.title = title;
		this.cost = cost;
	}

	public long getId() {
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

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Product)) return false;
		Product product = (Product) o;
		return id == product.id && Double.compare(product.cost, cost) == 0 &&
				title.equals(product.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, cost);
	}
}
