package ru.bulldog.eshop.dto;

import java.util.Set;
import java.util.UUID;

public class AuthResponse {
	private String firstname;
	private String lastname;
	private String token;
	private UUID session;
	private Set<String> authorities;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UUID getSession() {
		return session;
	}

	public void setSession(UUID session) {
		this.session = session;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
}
