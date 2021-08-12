package ru.bulldog.eshop.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bulldog.eshop.model.User;

import static ru.bulldog.eshop.util.EntityUtil.USER_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {}
