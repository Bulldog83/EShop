package ru.bulldog.eshop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.bulldog.eshop.model.Product;

import java.math.BigDecimal;

public class ProductSpecifications {

	public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
	}

	public static Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
	}

	public static Specification<Product> titleLike(String title) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
	}
}
