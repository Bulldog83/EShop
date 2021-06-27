package ru.bulldog.eshop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.ProductService;

import java.util.Optional;
import java.util.Scanner;

public class EShop {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(EShopConfig.class);

		Cart cart = context.getBean(Cart.class);
		ProductService productService = context.getBean(ProductService.class);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to shop!");
		while (true) {
			if (scanner.hasNext()) {
				String command = scanner.nextLine();
				if ("quit".equals(command)) {
					return;
				} else if (command.startsWith("put")) {
					try {
						long id = Long.parseLong(command.substring(4));
						Optional<Product> optionalProduct = productService.getById(id);
						if (optionalProduct.isPresent()) {
							Product product = optionalProduct.get();
							cart.put(product);
							System.out.println(product.getTitle() + " added to cart.");
						} else {
							System.out.println("Product not found.");
						}
					} catch (NumberFormatException ex) {
						System.out.println("Wrong product id format.");
					}
				} else if (command.startsWith("remove")) {
					try {
						long id = Long.parseLong(command.substring(6));
						Optional<Product> optionalProduct = productService.getById(id);
						if (optionalProduct.isPresent()) {
							Product product = optionalProduct.get();
							cart.remove(product.getId());
							System.out.println(product.getTitle() + " removed from cart.");
						} else {
							System.out.println("Product not found.");
						}
					} catch (NumberFormatException ex) {
						System.out.println("Wrong product id format.");
					}
				} else if ("print".equals(command)) {
					cart.printCart();
				} else if ("clear".equals(command)) {
					cart.clear();
					System.out.println("Cart cleared.");
				} else {
					System.out.println("Invalid command.");
				}
			}
		}
	}
}
