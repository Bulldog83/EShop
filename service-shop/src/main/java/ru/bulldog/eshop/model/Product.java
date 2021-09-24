package ru.bulldog.eshop.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
@NamedEntityGraph(
	name = "Product.forPages",
	attributeNodes = {
		@NamedAttributeNode("category"),
		@NamedAttributeNode("pictures")
	})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "raw_id", nullable = false)
	private Long id;
	@Column
	private String title;
	@Column
	private BigDecimal price = BigDecimal.ZERO;
	@Column
	@CreationTimestamp
	private LocalDateTime created;
	@Column
	@UpdateTimestamp
	private LocalDateTime updated;

	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;

	@ManyToMany
	@JoinTable(name = "products_pictures",
		joinColumns = @JoinColumn(name = "product_id"),
		inverseJoinColumns = @JoinColumn(name = "picture_id"))
	private List<Picture> pictures = new ArrayList<>();

	public Product() {}

	public Product(String title, BigDecimal price) {
		this.title = title;
		this.price = price;
	}

	public Product(long id, String title, BigDecimal price) {
		this.id = id;
		this.title = title;
		this.price = price;
	}

	public Product(long id, String title, BigDecimal price, Category category) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
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
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Product)) return false;
		Product product = (Product) o;
		if (id == null) {
			return product.id == null && title.equals(product.title);
		}
		return id.equals(product.id) && title.equals(product.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
