package ru.bulldog.eshop.util;

import ru.bulldog.eshop.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Validator {

	private final static Function<OrderDTO, ValidationResult> ORDER_VALIDATOR;

	public static ValidationResult validate(OrderDTO orderDTO) {
		return ORDER_VALIDATOR.apply(orderDTO);
	}

	static {
		ORDER_VALIDATOR = orderDTO -> {
			ValidationResult result = new ValidationResult();
			String address = orderDTO.getAddress();
			if (address == null || address.trim().equals("")) {
				result.addError("Address can't be empty.");
			}
			String phone = orderDTO.getPhone();
			if (phone == null) {
				result.addError("Phone can't be empty.");
			} else {
				phone = phone.trim();
				if (phone.equals("")) {
					result.addError("Phone can't be empty.");
				} else if (!phone.matches("[\\d-]+")) {
					result.addError("Wrong phone format.");
				}
			}
			return result;
		};
	}

	public static class ValidationResult {
		private final List<String> errors = new ArrayList<>();

		private ValidationResult() {}

		public void addError(String message) {
			errors.add(message);
		}

		public List<String> getErrors() {
			return errors;
		}

		public boolean isValid() {
			return errors.isEmpty();
		}
	}
}
