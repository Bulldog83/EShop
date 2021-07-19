package ru.bulldog.eshop.service;

import ru.bulldog.eshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
	Optional<Product> getById(long id);
	List<Product> getAll();
	List<Product> findByPrice(double min, double max);
	Product save(Product product);
	Product create(String title, double price);
	void delete(Product product);
	void delete(long id);
}
