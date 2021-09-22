package ru.bulldog.eshop.config;

import ru.bulldog.eshop.security.JwtRequestFilter;
import ru.bulldog.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtRequestFilter jwtRequestFilter;
	private UserService userService;

	@Autowired
	public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/v1/orders/new").permitAll()
				.antMatchers(HttpMethod.GET, "/api/v1/orders/**").authenticated()
				.antMatchers(HttpMethod.POST, "/api/v1/orders/**").hasAnyAuthority("ALL", "MANAGE_ORDERS")
				.antMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasAnyAuthority("ALL", "MANAGE_ORDERS")
				.antMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAnyAuthority("ALL", "MANAGE_ORDERS")
				.antMatchers(HttpMethod.POST, "/api/v1/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAnyAuthority("ALL", "MANAGE_PRODUCTS")
				.antMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.antMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.antMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAnyAuthority("ALL", "MANAGE_USERS")
				.anyRequest().permitAll()
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.headers()
					.frameOptions()
						.disable()
			.and()
				.exceptionHandling()
					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
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

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
