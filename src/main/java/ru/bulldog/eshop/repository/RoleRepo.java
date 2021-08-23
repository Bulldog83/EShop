package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bulldog.eshop.model.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Optional<Role> findByRoleName(String title);
}
