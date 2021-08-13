package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import javax.persistence.EntityNotFoundException;

import static ru.bulldog.eshop.util.DTOConverter.PRODUCT_TO_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public Page<ProductDTO> showProducts(@RequestParam(name = "page", defaultValue = "1") int pageIndex) {
		return productService.getPage(pageIndex - 1, 10).map(PRODUCT_TO_DTO_FACTORY);
	}

	@GetMapping("/{id}")
	public ProductDTO getProduct(@PathVariable long id) {
		Product product = productService.getById(id).orElseThrow(() -> new EntityNotFoundException("Product not found, id: " + id));
		return PRODUCT_TO_DTO_FACTORY.apply(product);
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
		return productService.getPageByPrice(minPrice, maxPrice, pageIndex - 1, 10).map(PRODUCT_TO_DTO_FACTORY);
	}
}
