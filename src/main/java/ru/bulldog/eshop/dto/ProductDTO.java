package ru.bulldog.eshop.dto;

import ru.bulldog.eshop.model.Product;

public class ProductDTO {
	private Long id;
	private String title;
	private double price;
	private String category;

	public ProductDTO() {}

	public ProductDTO(String title, double price, String category) {
		this.title = title;
		this.price = price;
		this.category = category;
	}

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.price = product.getPrice();
		this.category = product.getCategory().getTitle();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
