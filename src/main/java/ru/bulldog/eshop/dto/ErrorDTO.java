package ru.bulldog.eshop.dto;

import java.util.Date;

public class ErrorDTO {

	private String message;
	private Date timestamp;

	public ErrorDTO(String message) {
		this.timestamp = new Date();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
