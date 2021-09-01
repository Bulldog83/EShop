package ru.bulldog.eshop.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorDTO {

	private List<String> errors;
	private Date timestamp;

	public ErrorDTO() {
		this.errors = new ArrayList<>();
		this.timestamp = new Date();
	}

	public ErrorDTO(String message) {
		this();
		this.errors.add(message);
	}

	public ErrorDTO(List<String> errors) {
		this.timestamp = new Date();
		this.errors = errors;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
