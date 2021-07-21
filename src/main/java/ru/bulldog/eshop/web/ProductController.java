package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public Page<Product> showProducts(@RequestParam(name = "page", defaultValue = "1") int pageIndex) {
		return productService.getPage(pageIndex - 1, 10);
	}

	@GetMapping("/products/{id}")
	public String showProduct(@PathVariable long id, Model model) {
		Optional<Product> productOptional = productService.getById(id);
		if (productOptional.isPresent()) {
			model.addAttribute("product", productOptional.get());
			return "product";
		}
		return "redirect:/products";
	}

	@GetMapping("/products/new")
	public String newProductForm() {
		return "new-product";
	}

	@PostMapping("/products/new")
	public String addNewProduct(@RequestParam String title, @RequestParam double price) {
		productService.create(title, price);
		return "redirect:/products";
	}

	@GetMapping("/products/delete/{id}")
	public void deleteProduct(@PathVariable long id) {
		productService.delete(id);
	}

	@GetMapping("/products/filter")
	public Page<Product> filterProducts(@RequestParam("min") double minPrice, @RequestParam("max") double maxPrice, @RequestParam(name = "page", defaultValue = "1") int pageIndex) {
		return productService.getPageByPrice(minPrice, maxPrice, pageIndex - 1, 10);
	}
}
