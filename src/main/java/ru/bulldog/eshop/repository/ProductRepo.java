package ru.bulldog.eshop.repository;

import org.springframework.stereotype.Component;
import ru.bulldog.eshop.model.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRepo implements EntityRepo<Product> {

	private List<Product> products;

	@PostConstruct
	private void init() {
		products = new ArrayList<>(Arrays.asList(
				new Product(1, "Blue Pen", 5.45),
				new Product(2, "Red Pen", 5.45),
				new Product(3, "Green Pen", 5.45),
				new Product(4, "Pencils Box", 20.0),
				new Product(5, "Paper Block", 10.25),
				new Product(6, "Notebook", 3.15),
				new Product(7, "Notebook", 3.15),
				new Product(8, "Notebook", 2.95),
				new Product(9, "Notebook", 2.85),
				new Product(10, "Notebook", 4.0),
				new Product(11, "Scratchpad", 2.15),
				new Product(12, "Scratchpad", 2.15),
				new Product(13, "Scratchpad", 2.2),
				new Product(14, "Scotch tape", 1.25),
				new Product(15, "Scotch tape", 1.75),
				new Product(16, "Scotch tape", 2.25),
				new Product(17, "Scotch tape", 4.5),
				new Product(18, "Paints", 3.0),
				new Product(19, "Paints", 3.55),
				new Product(20, "Paints", 4.5),
				new Product(21, "Paints", 5.25),
				new Product(22, "Paints", 6.15),
				new Product(23, "Set of pens", 12.45),
				new Product(24, "Set of pens", 13.15),
				new Product(25, "Set of pens", 13.5),
				new Product(26, "Set of pens", 13.8),
				new Product(27, "Ruler", 1.45),
				new Product(28, "Ruler", 1.5),
				new Product(29, "Ruler", 2.0),
				new Product(30, "Ruler", 2.45)
		));
	}

	@Override
	public Optional<Product> getOne(long id) {
		return products.stream().filter(product -> product.getId() == id).findFirst();
	}

	@Override
	public List<Product> getAll() {
		return products;
	}

	public Product save(Product product) {
		if (products.contains(product)) {
			int idx = products.indexOf(product);
			Product exists = products.get(idx);
			product.setId(exists.getId());
			products.set(idx, product);
			return product;
		}
		product.setId(products.size() + 1);
		products.add(product);
		return product;
	}
}
