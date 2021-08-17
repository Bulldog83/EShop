package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.dto.AuthRequest;
import ru.bulldog.eshop.dto.AuthResponse;
import ru.bulldog.eshop.model.User;
import ru.bulldog.eshop.service.UserService;
import ru.bulldog.eshop.util.JwtToken;
import ru.bulldog.eshop.util.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class LoginController {
	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.authenticationManager = authenticationManager;
	}

	@GetMapping("/session")
	public UUID getNewSession() {
		return UUID.randomUUID();
	}

	@GetMapping("/login")
	public ResponseEntity<?> checkLogin(HttpServletRequest request) {
		Optional<JwtToken> tokenOptional = jwtTokenUtil.getJwtToken(request);
		if (tokenOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody AuthRequest authRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		User user = (User) userService.loadUserByUsername(authRequest.getUsername());
		String tokenString = jwtTokenUtil.generateTokenString(user);
		AuthResponse response = new AuthResponse();
		response.setFirstname(user.getFirstName());
		response.setLastname(user.getLastName());
		response.setToken(tokenString);
		response.setSession(user.getSessionId());
		response.setAuthorities(user.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
		return ResponseEntity.ok(response);
	}
}
