package ru.bulldog.eshop.soap.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.bulldog.eshop.soap.service.ProductService;
import ru.bulldog.eshop.ws.products.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.bulldog.eshop.soap.service.ProductService.PRODUCT_TO_WS_FACTORY;

@Endpoint
public class ProductsEndpoint {
	private static final String NAMESPACE_URI = "http://www.bulldog.ru/eshop/ws/products";

	private final ProductService productService;

	@Autowired
	public ProductsEndpoint(ProductService productService) {
		this.productService = productService;
	}

	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
	public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
		GetProductByIdResponse response = new GetProductByIdResponse();
		Optional<ProductWS> productOptional = productService.getById(request.getId()).map(PRODUCT_TO_WS_FACTORY);
		productOptional.ifPresent(response::setProduct);
		return response;
	}

	@ResponsePayload
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
	public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
		GetAllProductsResponse response = new GetAllProductsResponse();
		List<ProductWS> productsWS = productService.getAll().stream().map(PRODUCT_TO_WS_FACTORY).collect(Collectors.toList());
		response.getProducts().addAll(productsWS);
		return response;
	}
}
