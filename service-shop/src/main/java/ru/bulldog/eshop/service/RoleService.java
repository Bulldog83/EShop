package ru.bulldog.eshop.service;

import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Role;

import java.util.Optional;

@Repository
public interface RoleService {
	Optional<Role> findByName(String roleName);
}
