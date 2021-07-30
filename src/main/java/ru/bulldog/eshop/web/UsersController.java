package ru.bulldog.eshop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.model.User;

import static ru.bulldog.eshop.util.EntityUtil.USER_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

	@GetMapping("/current")
	public ResponseEntity<?> getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return new ResponseEntity<>(USER_DTO_FACTORY.apply((User) principal), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
