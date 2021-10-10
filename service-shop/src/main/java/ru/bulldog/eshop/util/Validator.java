package ru.bulldog.eshop.util;

import ru.bulldog.eshop.dto.AddressDTO;
import ru.bulldog.eshop.dto.OrderDTO;
import ru.bulldog.eshop.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class Validator {

	private static final Function<AddressDTO, ValidationResult> ADDRESS_VALIDATOR;
	private static final Function<OrderDTO, ValidationResult> ORDER_VALIDATOR;
	private static final Function<UserDTO, ValidationResult> USER_VALIDATOR;

	public static ValidationResult validate(OrderDTO orderDTO) {
		return ORDER_VALIDATOR.apply(orderDTO);
	}

	public static ValidationResult validate(UserDTO userDTO) {
		return USER_VALIDATOR.apply(userDTO);
	}

	public static class ValidationResult {
		private final List<String> errors = new ArrayList<>();

		private ValidationResult() {}

		public void addError(String message) {
			errors.add(message);
		}

		public void addErrors(Collection<String> errors) {
			this.errors.addAll(errors);
		}

		public List<String> getErrors() {
			return errors;
		}

		public boolean hasErrors() {
			return !errors.isEmpty();
		}
	}

	static {
		String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
				"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@" +
				"(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[" +
				"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
				"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

		ADDRESS_VALIDATOR = addressDTO -> {
			ValidationResult result = new ValidationResult();
			String country = addressDTO.getCountry();
			if (country == null) {
				result.addError("Country can't be null.");
			} else {
				country = country.trim();
				if (country.equals("")) {
					result.addError("Country can't be empty.");
				} else if (!country.matches("[A-Z]{2}")) {
					result.addError("Wrong country code format.");
				}
			}
			String city = addressDTO.getCity();
			if (city == null) {
				result.addError("City can't be null.");
			} else if (city.trim().equals("")) {
				result.addError("City can't be empty.");
			}
			String street = addressDTO.getStreet();
			if (street == null) {
				result.addError("Street can't be null.");
			} else if (street.trim().equals("")) {
				result.addError("Street can't be empty.");
			}
			String house = addressDTO.getHouse();
			if (house == null) {
				result.addError("House can't be null.");
			} else if (house.trim().equals("")) {
				result.addError("House can't be empty.");
			}
			return result;
		};

		ORDER_VALIDATOR = orderDTO -> {
			ValidationResult result = new ValidationResult();
			ValidationResult addressValidation = ADDRESS_VALIDATOR.apply(orderDTO.getAddress());
			if (result.hasErrors()) {
				result.addErrors(addressValidation.getErrors());
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

		USER_VALIDATOR = userDTO -> {
			ValidationResult result = new ValidationResult();
			String username = userDTO.getUsername();
			if (username == null || username.trim().equals("")) {
				result.addError("E-mail can't be empty.");
			} else if (!username.matches(emailPattern)) {
				result.addError("Invalid e-mail format.");
			}
			String password = userDTO.getPassword();
			if (password == null || password.trim().equals("")) {
				result.addError("Password can't be empty.");
			} else if (password.matches("\\d+")) {
				result.addError("Password can't containing only digits.");
			} else if (password.length() < 8) {
				result.addError("Password must be at least 8 characters long.");
			}
			return result;
		};
	}
}
