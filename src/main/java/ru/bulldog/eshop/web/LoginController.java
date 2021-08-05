package ru.bulldog.eshop.web;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@GetMapping("/token")
	public CsrfToken getToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
}
