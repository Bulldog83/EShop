package ru.bulldog.eshop.service.impl;

import ru.bulldog.eshop.model.User;
import ru.bulldog.eshop.repository.UserRepo;
import ru.bulldog.eshop.service.RoleService;
import ru.bulldog.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bulldog.eshop.dto.UserDTO;
import ru.bulldog.eshop.util.EntityConverter;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepo repository;
	private final RoleService roleService;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepo repository, RoleService roleService) {
		this.repository = repository;
		this.roleService = roleService;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<User> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public List<User> getAll() {
		return repository.findAll();
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public User create(UserDTO userDTO) {
		String passwordEncoded = passwordEncoder.encode(userDTO.getPassword());
		User user = EntityConverter.USER_FACTORY.apply(userDTO);
		user.setPassword(passwordEncoded);
		roleService.findByName("ROLE_CUSTOMER").ifPresent(role ->
				user.setRoles(Collections.singletonList(role)));
		return repository.save(user);
	}

	@Override
	public boolean isUsernameExists(String username) {
		return repository.existsByUsername(username);
	}

	@Override
	public boolean isUserSessionExists(UUID sessionId) {
		return repository.existsBySessionId(sessionId);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username).orElseThrow(() ->
				new UsernameNotFoundException(String.format("Username %s not found.", username)));
		Set<GrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.addAll(role.getPermissions());
			authorities.add(role);
		});
		user.setAuthorities(authorities);
		return user;
	}
}
