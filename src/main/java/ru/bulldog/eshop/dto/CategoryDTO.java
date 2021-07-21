package ru.bulldog.eshop.dto;

import ru.bulldog.eshop.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO {
	private Long id;
	private String title;
	private List<ProductDTO> products;

	public CategoryDTO() {
		this.products = new ArrayList<>();
	}

	public CategoryDTO(String title) {
		this();
		this.title = title;
	}

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.title = category.getTitle();
		this.products = category.getProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
}
