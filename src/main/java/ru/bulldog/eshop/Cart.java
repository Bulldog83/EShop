package ru.bulldog.eshop;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.bulldog.eshop.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Cart {

	private List<Product> products;

	public Cart() {}

	@PostConstruct
	private void init() {
		products = new ArrayList<>();
	}

	public void put(Product product) {
		products.add(product);
	}

	public void remove(long id) {
		Iterator<Product> productIterator = products.listIterator();
		while (productIterator.hasNext()) {
			Product product = productIterator.next();
			if (product.getId() == id) {
				productIterator.remove();
				break;
			}
		}
	}

	public void printCart() {
		if (products.isEmpty()) {
			System.out.println("Cart is empty.");
		} else {
			StringBuilder outBuilder = new StringBuilder("Products in cart:\n");
			double sum = 0.0;
			for (int i = 0, size = products.size(); i < size; i++) {
				Product product = products.get(i);
				double cost = product.getCost();
				outBuilder.append(i + 1).append(". ")
						.append(product.getTitle()).append(": ")
						.append(cost).append("\n");
				sum += cost;
			}
			outBuilder.append("==========\n")
					.append("Total: ").append(sum);
			System.out.println(outBuilder);
		}
	}

	public void clear() {
		products.clear();
	}
}
