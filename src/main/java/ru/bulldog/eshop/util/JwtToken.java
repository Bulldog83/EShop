package ru.bulldog.eshop.util;

import java.util.Set;
import java.util.UUID;

public class JwtToken {

	private String username;
	private Set<String> authorities;
	private UUID session;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public UUID getSession() {
		return session;
	}

	public void setSession(UUID session) {
		this.session = session;
	}
}
