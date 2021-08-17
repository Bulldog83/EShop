package ru.bulldog.eshop.service.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.repository.specifications.ProductSpecifications;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ProductSpecificationService extends SpecificationService<Product> {

	@Override
	public Specification<Product> buildSpecification(Map<String, String> paramsMap) {
		SpecificationBuilder builder = new SpecificationBuilder();
		paramsMap.forEach((param, value) -> {
			Specification<Product> specification = null;
			switch (param) {
				case "minPrice":
					BigDecimal minPrice = new BigDecimal(value);
					specification = ProductSpecifications.priceGreaterOrEqualsThan(minPrice);
					break;
				case "maxPrice":
					BigDecimal maxPrice = new BigDecimal(value);
					specification = ProductSpecifications.priceLessOrEqualsThan(maxPrice);
					break;
				case "title":
					specification = ProductSpecifications.titleLike(value);
					break;
			}
			if (specification != null) {
				builder.addSpecification(specification);
			}
		});
		return builder.build();
	}
}
