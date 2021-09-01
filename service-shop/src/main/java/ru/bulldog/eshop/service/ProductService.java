package ru.bulldog.eshop.service;

import ru.bulldog.eshop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.bulldog.eshop.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
	Optional<Product> getById(long id);
	List<Product> getAll();
	Page<Product> getPage(int index, int elements, Specification<Product> specification);
	Product save(Product product);
	Product create(String title, BigDecimal price, long category);
	Product create(ProductDTO productDTO);
	void delete(Product product);
	void delete(long id);
}
