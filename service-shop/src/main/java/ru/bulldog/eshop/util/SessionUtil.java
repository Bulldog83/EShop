package ru.bulldog.eshop.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

public class SessionUtil {

	public static Optional<UUID> getSession(HttpServletRequest request) {
		String sessionId = request.getHeader("X-SESSION");
		if (sessionId != null) {
			try {
				return Optional.of(UUID.fromString(sessionId));
			} catch (Exception ex) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	private SessionUtil() {}
}
