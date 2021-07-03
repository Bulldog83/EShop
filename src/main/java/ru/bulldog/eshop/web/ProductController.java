package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import java.util.Optional;

@Controller
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/")
	public String mainPage() {
		return "redirect:/products";
	}

	@GetMapping("/products")
	public String allProducts(Model model) {
		model.addAttribute("products", productService.getAll());
		return "index";
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
}
