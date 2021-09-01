package ru.bulldog.eshop.dto;

public class CategoryDTO {
	private Long id;
	private String title;

	public CategoryDTO() {}

	public CategoryDTO(String title) {
		this.title = title;
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
}
