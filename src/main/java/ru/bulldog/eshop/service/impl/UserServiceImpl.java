package ru.bulldog.eshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bulldog.eshop.dto.UserDTO;
import ru.bulldog.eshop.model.User;
import ru.bulldog.eshop.repository.UserRepo;
import ru.bulldog.eshop.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepo repository;

	@Autowired
	public UserServiceImpl(UserRepo repository) {
		this.repository = repository;
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
		return null;
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
