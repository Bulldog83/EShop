package ru.bulldog.eshop.dto;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

public class ProductDTO {

	private Long id;
	private String title;
	private BigDecimal price;
	private String category;
	private List<URL> pictures;

	public ProductDTO() {}

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
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<URL> getPictures() {
		return pictures;
	}

	public void setPictures(List<URL> pictures) {
		this.pictures = pictures;
	}
}
