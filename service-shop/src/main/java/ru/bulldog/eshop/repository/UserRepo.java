package ru.bulldog.eshop.repository;

import ru.bulldog.eshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findBySessionId(UUID sessionId);
	boolean existsByUsername(String username);
	boolean existsBySessionId(UUID sessionId);
}
