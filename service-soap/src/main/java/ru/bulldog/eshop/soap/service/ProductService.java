package ru.bulldog.eshop.soap.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.ws.products.ProductWS;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class ProductService {

	public final static Function<ProductDTO, ProductWS> PRODUCT_TO_WS_FACTORY = product -> {
		ProductWS productWS = new ProductWS();
		productWS.setId(product.getId());
		productWS.setTitle(product.getTitle());
		productWS.setPrice(product.getPrice().doubleValue());

		return productWS;
	};

	private WebClient webClient;

	@PostConstruct
	private void postInit() {
		this.webClient = WebClient.create("http://service-main");
	}

	public List<ProductDTO> getAll() {
		try {
			CompletableFuture<List<ProductDTO>> futureProducts = new CompletableFuture<>();
			Flux<ProductDTO> productsFlux = webClient.get()
					.uri("/api/v1/products/all")
					.retrieve()
					.bodyToFlux(ProductDTO.class);
			Mono<List<ProductDTO>> productsMono = productsFlux.collectList();
			productsFlux.doOnError(futureProducts::completeExceptionally);
			productsMono.doOnSuccess(futureProducts::complete);
			return futureProducts.join();
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	public Optional<ProductDTO> getById(long id) {
		try {
			CompletableFuture<ProductDTO> futureProduct = new CompletableFuture<>();
			Mono<ProductDTO> productMono = webClient.get()
					.uri("/api/v1/products/" + id)
					.retrieve()
					.bodyToMono(ProductDTO.class);
			productMono.doOnSuccess(futureProduct::complete);
			productMono.doOnError(futureProduct::completeExceptionally);
			return Optional.of(futureProduct.join());
		} catch (Exception ex) {
			return Optional.empty();
		}
	}
}
