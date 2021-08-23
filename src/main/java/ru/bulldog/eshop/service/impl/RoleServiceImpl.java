package ru.bulldog.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Role;
import ru.bulldog.eshop.repository.RoleRepo;
import ru.bulldog.eshop.service.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepo repository;

	@Autowired
	public RoleServiceImpl(RoleRepo repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Role> findByName(String roleName) {
		return repository.findByRoleName(roleName);
	}
}
