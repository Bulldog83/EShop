package ru.bulldog.eshop.soap.util;

import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.ws.products.ProductWS;

import java.util.function.Function;

public class EntityConverter {

	public final static Function<ProductDTO, ProductWS> PRODUCT_TO_WS_FACTORY = product -> {
		ProductWS productWS = new ProductWS();
		productWS.setId(product.getId());
		productWS.setTitle(product.getTitle());
		productWS.setPrice(product.getPrice().doubleValue());

		return productWS;
	};
}
