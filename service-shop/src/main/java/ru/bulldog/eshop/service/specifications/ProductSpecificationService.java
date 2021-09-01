package ru.bulldog.eshop.service.specifications;

import ru.bulldog.eshop.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ProductSpecificationService extends SpecificationService<Product> {

	private static Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
	}

	private static Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
	}

	private static Specification<Product> titleLike(String title) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
	}

	@Override
	public Specification<Product> buildSpecification(Map<String, String> paramsMap) {
		SpecificationBuilder builder = new SpecificationBuilder();
		paramsMap.forEach((param, value) -> {
			Specification<Product> specification = null;
			switch (param) {
				case "minPrice":
					BigDecimal minPrice = new BigDecimal(value);
					specification = priceGreaterOrEqualsThan(minPrice);
					break;
				case "maxPrice":
					BigDecimal maxPrice = new BigDecimal(value);
					specification = priceLessOrEqualsThan(maxPrice);
					break;
				case "title":
					specification = titleLike(value);
					break;
			}
			if (specification != null) {
				builder.addSpecification(SpecRule.AND, specification);
			}
		});
		return builder.build();
	}
}
