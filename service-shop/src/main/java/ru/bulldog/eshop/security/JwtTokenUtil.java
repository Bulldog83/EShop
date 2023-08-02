package ru.bulldog.eshop.security;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bulldog.eshop.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenUtil {

	private static final Logger logger = LogManager.getLogger(JwtTokenUtil.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.lifetime}")
	private Integer jwtLifetime;

	public String generateTokenString(UserDTO user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", user.getAuthorities());
		claims.put("session", user.getSessionId());

		Date createdDate = new Date();
		Date expiredDate = new Date(createdDate.getTime() + jwtLifetime);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(user.getUsername())
				.setIssuedAt(createdDate)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	@SuppressWarnings("unchecked")
	public Optional<JwtToken> getJwtToken(HttpServletRequest request) throws ExpiredJwtException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String tokenString = authHeader.substring(7);
			try {
				Claims claims = decodeClaims(tokenString);
				Collection<String> authorities = claims.get("authorities", List.class);
				String sessionId = claims.get("session", String.class);
				JwtToken jwtToken = new JwtToken();
				jwtToken.setUsername(claims.getSubject());
				jwtToken.setAuthorities(new HashSet<>(authorities));
				jwtToken.setSession(UUID.fromString(sessionId));
				return Optional.of(jwtToken);
			} catch (JwtException jwe) {
				logger.warn("Invalid token: {}", jwe.getMessage());
			} catch (Exception ex) {
				logger.error("Decode token error.", ex);
			}
		}
		return Optional.empty();
	}

	private Claims decodeClaims(String token) throws JwtException {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
}
