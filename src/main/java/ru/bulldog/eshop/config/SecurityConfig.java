package ru.bulldog.eshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.bulldog.eshop.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	@Autowired
	public SecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.PUT, "/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.DELETE, "/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority("ALL", "MANAGE_ORDERS")
				.antMatchers(HttpMethod.DELETE, "/orders/**").hasAnyAuthority("ALL", "MANAGE_ORDERS")
				.antMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.antMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.antMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.and()
				.logout();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userService);
		return authenticationProvider;
	}
}
