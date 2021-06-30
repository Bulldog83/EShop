package ru.bulldog.eshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

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

	@GetMapping("/new")
	public String newProductForm() {
		return "new-product";
	}

	@PostMapping("/new")
	public String addNewProduct(@RequestParam String title, @RequestParam double price) {
		Product product = new Product(title, price);
		productService.save(product);
		return "redirect:/products";
	}
}
