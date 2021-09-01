package ru.bulldog.eshop.service.impl;

import ru.bulldog.eshop.repository.ProductRepo;
import ru.bulldog.eshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Category;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepo repository;
	private final CategoryService categoryService;

	@Autowired
	public ProductServiceImpl(ProductRepo repository, CategoryService categoryService) {
		this.categoryService = categoryService;
		this.repository = repository;
	}

	@Override
	public Optional<Product> getById(long id) {
		return repository.findById(id);
	}

	@Override
	public List<Product> getAll() {
		return repository.findAll();
	}

	@Override
	public Page<Product> getPage(int index, int elements, Specification<Product> specification) {
		return repository.findAll(specification, PageRequest.of(index, elements));
	}

	@Override
	public Product save(Product product) {
		return repository.save(product);
	}

	@Override
	public Product create(String title, BigDecimal price, long categoryId) {
		Product product = new Product(title, price);
		Category category = categoryService.getById(categoryId);
		product.setCategory(category);
		return repository.save(product);
	}

	@Override
	public Product create(ProductDTO productDTO) {
		Product product = new Product();
		Category category = categoryService.findByTitle(productDTO.getCategory())
										   .orElse(categoryService.getById(1));
		product.setTitle(productDTO.getTitle());
		product.setPrice(productDTO.getPrice());
		product.setCategory(category);

		return repository.save(product);
	}

	@Override
	public void delete(Product product) {
		repository.delete(product);
	}

	@Override
	public void delete(long id) {
		repository.deleteById(id);
	}
}
