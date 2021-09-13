package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;
import ru.bulldog.eshop.service.specifications.ProductSpecificationService;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.bulldog.eshop.util.EntityConverter.PRODUCT_TO_DTO_FACTORY;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;
	private final ProductSpecificationService specificationService;

	@Autowired
	public ProductController(ProductService productService, ProductSpecificationService specificationService) {
		this.productService = productService;
		this.specificationService = specificationService;
	}

	@GetMapping
	public Page<ProductDTO> showProducts(@RequestParam Map<String, String> params) {
		int pageIndex = 0;
		if (params.containsKey("page")) {
			pageIndex = Integer.parseInt(params.remove("page"));
		}
		Specification<Product> specification = specificationService.buildSpecification(params);
		return productService.getPage(pageIndex - 1, 10, specification).map(PRODUCT_TO_DTO_FACTORY);
	}

	@GetMapping("/all")
	public List<ProductDTO> getAllProducts() {
		return productService.getAll().stream().map(PRODUCT_TO_DTO_FACTORY).collect(Collectors.toList());
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
}
