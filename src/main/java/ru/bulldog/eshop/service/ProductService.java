package ru.bulldog.eshop.service;

import org.springframework.data.domain.Page;
import ru.bulldog.eshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
	Optional<Product> getById(long id);
	List<Product> getAll();
	List<Product> findByPrice(double min, double max);
	Page<Product> getPage(int index, int elements);
	Page<Product> getPageByPrice(double min, double max, int index, int elements);
	Product save(Product product);
	Product create(String title, double price);
	void delete(Product product);
	void delete(long id);
}
