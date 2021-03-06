package ru.bulldog.eshop.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NamedEntityGraph(name = "Order.withData",
	attributeNodes = {
		@NamedAttributeNode("items"),
		@NamedAttributeNode("address")
	}
)
public class Order {
	@Id
	@Column(name = "raw_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "session")
	private UUID sessionId;
	@Column
	private String phone;
	@Column(name = "sum")
	private BigDecimal sumTotal;
	@Column
	private Integer status;
	@Column
	@CreationTimestamp
	private LocalDateTime created;
	@Column
	@UpdateTimestamp
	private LocalDateTime updated;

	@ManyToOne
	@JoinColumn(name = "address")
	private Address address;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> items;

	public Order() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(BigDecimal sumTotal) {
		this.sumTotal = sumTotal;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}
}
