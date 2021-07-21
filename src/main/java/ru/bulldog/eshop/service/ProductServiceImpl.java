package ru.bulldog.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepo repository;

	@Autowired
	public ProductServiceImpl(ProductRepo repository) {
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
	public Page<Product> getPage(int index, int elements) {
		return repository.findAll(PageRequest.of(index, elements));
	}

	@Override
	public List<Product> findByPrice(double min, double max) {
		if (min <= 0.0 && max <= 0.0) {
			return getAll();
		}
		if (min <= 0.0) {
			return repository.findAllByPriceLessThanEqual(max);
		}
		if (max <= 0.0) {
			return repository.findAllByPriceGreaterThanEqual(min);
		}
		return repository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max);
	}

	@Override
	public Page<Product> getPageByPrice(double min, double max, int index, int elements) {
		if (min <= 0.0 && max <= 0.0) {
			return getPage(index, elements);
		}
		if (min <= 0.0) {
			return repository.findAllByPriceLessThanEqual(max, PageRequest.of(index, elements));
		}
		if (max <= 0.0) {
			return repository.findAllByPriceGreaterThanEqual(min, PageRequest.of(index, elements));
		}
		return repository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max, PageRequest.of(index, elements));
	}

	@Override
	public Product save(Product product) {
		return repository.save(product);
	}

	@Override
	public Product create(String title, double price) {
		Product product = new Product(title, price);
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
