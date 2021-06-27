package ru.bulldog.eshop.model;

public class Product {

	private long id;
	private String title;
	private double cost;

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
}
