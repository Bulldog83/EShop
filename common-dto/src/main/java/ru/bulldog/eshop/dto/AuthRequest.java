package ru.bulldog.eshop.dto;

import java.util.UUID;

public class AuthRequest {
	private String username;
	private String password;
	private UUID session;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UUID getSession() {
		return session;
	}

	public void setSession(UUID session) {
		this.session = session;
	}
}
