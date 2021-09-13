package ru.bulldog.eshop.service;

import ru.bulldog.eshop.model.Category;

import java.util.Optional;

public interface CategoryService {
	Category getById(long id);
	Optional<Category> findById(long id);
	Optional<Category> findByTitle(String title);
}
