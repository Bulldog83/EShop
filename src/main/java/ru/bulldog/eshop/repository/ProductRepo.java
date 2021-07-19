package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Product;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	List<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(double minPrice, double maxPrice);
	List<Product> findAllByPriceGreaterThanEqual(double minPrice);
	List<Product> findAllByPriceLessThanEqual(double maxPrice);
}
