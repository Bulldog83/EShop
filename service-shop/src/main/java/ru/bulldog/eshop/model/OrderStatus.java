package ru.bulldog.eshop.model;

public enum OrderStatus {
	NEW(1, "NEW"),
	IN_PROCESS(2, "IN_PROCESS"),
	PAYMENT_WAITING(3, "PAYMENT_WAITING"),
	PAID(4, "PAID"),
	COMPLETED(5, "COMPLETED"),
	CANCELED(6, "CANCELED");

	public static OrderStatus of(String status) {
		for(OrderStatus value : values()) {
			if (value.getStatus().equals(status)) {
				return value;
			}
		}
		throw new IllegalArgumentException("No status '" + status + "' found.");
	}

	public static OrderStatus of(int idx) {
		for(OrderStatus value : values()) {
			if (value.getIndex() == idx) {
				return value;
			}
		}
		throw new IllegalArgumentException("No status with index '" + idx + "' found.");
	}

	private final int idx;
	private final String status;

	OrderStatus(int idx, String status) {
		this.idx = idx;
		this.status = status;
	}

	public int getIndex() {
		return idx;
	}

	public String getStatus() {
		return status;
	}
}
