package ru.bulldog.eshop.soap.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bulldog.eshop.dto.ProductDTO;
import bulldog.eshop.ws.products.ProductWS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class ProductService {

	private static final String BASE_URI = "lb://service-shop/api/v1/products";

	public static final Function<ProductDTO, ProductWS> PRODUCT_TO_WS_FACTORY = product -> {
		ProductWS productWS = new ProductWS();
		productWS.setId(product.getId());
		productWS.setTitle(product.getTitle());
		productWS.setPrice(product.getPrice().doubleValue());

		return productWS;
	};

	private final WebClient webClient;

	public ProductService(WebClient webClient) {
		this.webClient = webClient;
	}

	public List<ProductDTO> getAll() {
		try {
			CompletableFuture<List<ProductDTO>> futureProducts = new CompletableFuture<>();
			Flux<ProductDTO> productsFlux = webClient.get()
					.uri(BASE_URI + "/all")
					.retrieve()
					.bodyToFlux(ProductDTO.class);
			Mono<List<ProductDTO>> productsMono = productsFlux.collectList();
			productsMono.subscribe(futureProducts::complete, futureProducts::completeExceptionally);
			return futureProducts.join();
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public Optional<ProductDTO> getById(long id) {
		try {
			CompletableFuture<ProductDTO> futureProduct = new CompletableFuture<>();
			Mono<ProductDTO> productMono = webClient.get()
					.uri(BASE_URI + "/" + id)
					.retrieve()
					.bodyToMono(ProductDTO.class);
			productMono.subscribe(futureProduct::complete, futureProduct::completeExceptionally);
			return Optional.of(futureProduct.join());
		} catch (Exception ex) {
			return Optional.empty();
		}
	}
}
