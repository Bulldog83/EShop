package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.dto.UserDTO;
import ru.bulldog.eshop.errors.EntityValidationException;
import ru.bulldog.eshop.service.UserService;
import ru.bulldog.eshop.util.Validator;
import ru.bulldog.eshop.util.Validator.ValidationResult;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	private final UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userData) {
		ValidationResult validationResult = Validator.validate(userData);
		if (validationResult.isValid()) {
			if (userService.isUsernameExists(userData.getUsername())) {
				throw new EntityValidationException("User with email " + userData.getUsername() + " exists.");
			}
			if (userService.isUserSessionExists(userData.getSessionId())) {
				throw new EntityValidationException("You've already registered, but didn't logged in.");
			}
			userService.create(userData);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		throw new EntityValidationException(validationResult.getErrors());
	}
}
