package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Category;
import ru.bulldog.eshop.repository.CategoryRepo;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepo repository;

	@Autowired
	public CategoryServiceImpl(CategoryRepo repository) {
		this.repository = repository;
	}

	@Override
	public Category getById(long id) {
		return repository.getById(id);
	}

	@Override
	public Optional<Category> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Category> findByTitle(String title) {
		return repository.findByTitle(title);
	}
}
