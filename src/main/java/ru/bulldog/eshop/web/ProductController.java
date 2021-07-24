package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import java.util.function.Function;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final static Function<Product, ProductDTO> productFactory = product -> {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setTitle(product.getTitle());
		productDTO.setCategory(product.getCategory().getTitle());
		productDTO.setPrice(product.getPrice());

		return productDTO;
	};

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public Page<ProductDTO> showProducts(@RequestParam(name = "page", defaultValue = "1") int pageIndex) {
		return productService.getPage(pageIndex - 1, 10).map(productFactory);
	}

	@PostMapping
	public ProductDTO addNewProduct(@RequestBody ProductDTO productDTO) {
		Product product = productService.create(productDTO);
		productDTO.setId(product.getId());
		return productDTO;
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable long id) {
		productService.delete(id);
	}

	@GetMapping("/filter")
	public Page<ProductDTO> filterProducts(@RequestParam("min") double minPrice, @RequestParam("max") double maxPrice, @RequestParam(name = "page", defaultValue = "1") int pageIndex) {
		return productService.getPageByPrice(minPrice, maxPrice, pageIndex - 1, 10).map(productFactory);
	}
}
