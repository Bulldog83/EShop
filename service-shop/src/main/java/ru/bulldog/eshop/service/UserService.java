package ru.bulldog.eshop.service;

import ru.bulldog.eshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.bulldog.eshop.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
	Optional<User> findById(long id);
	List<User> getAll();
	User save(User user);
	User create(UserDTO userDTO);
	boolean isUsernameExists(String username);
	boolean isUserSessionExists(UUID sessionId);
}
