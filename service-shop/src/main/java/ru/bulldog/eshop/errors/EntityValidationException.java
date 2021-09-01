package ru.bulldog.eshop.errors;

import java.util.Collections;
import java.util.List;

public class EntityValidationException extends RuntimeException {

	private List<String> errors;

	public EntityValidationException(List<String> errors) {
		this.errors = errors;
	}

	public EntityValidationException(String message) {
		super(message);
		this.errors = Collections.singletonList(message);
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}
}
