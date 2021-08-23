package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bulldog.eshop.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsBySessionId(UUID sessionId);
}
