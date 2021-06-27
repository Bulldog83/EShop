package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

	private final ProductRepo repository;

	@Autowired
	public ProductServiceImpl(ProductRepo repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Product> getById(long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Product> getAll() {
		return repository.getAll();
	}
}
