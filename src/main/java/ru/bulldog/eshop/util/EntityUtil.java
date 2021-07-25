package ru.bulldog.eshop.util;

import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Product;

import java.util.function.Function;

public class EntityUtil {

	public final static Function<Product, ProductDTO> PRODUCT_FACTORY = product -> {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setTitle(product.getTitle());
		productDTO.setCategory(product.getCategory().getTitle());
		productDTO.setPrice(product.getPrice());

		return productDTO;
	};
}
