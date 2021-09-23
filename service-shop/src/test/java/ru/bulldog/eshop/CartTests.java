package ru.bulldog.eshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.bulldog.eshop.dto.CartDTO;
import ru.bulldog.eshop.dto.ProductDTO;
import ru.bulldog.eshop.model.Category;
import ru.bulldog.eshop.model.Product;
import ru.bulldog.eshop.service.CartService;
import ru.bulldog.eshop.service.ProductService;
import ru.bulldog.eshop.util.EntityConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ru.bulldog.eshop.util.EntityConverter.PRODUCT_TO_DTO_FACTORY;

@SpringBootTest
public class CartTests {

	@MockBean
	private ProductService productService;
	@Autowired
	private CartService cartService;

	private static UUID sessionId;
	private static Map<Long, Product> products;

	@BeforeAll
	public static void prepare() {
		sessionId = UUID.randomUUID();
		products = new HashMap<>();
		Category category = new Category(1L, "Other");
		products.put(1L, new Product(1L, "Notebook", BigDecimal.valueOf(3.15), category));
		products.put(2L, new Product(2L, "Blue Pen", BigDecimal.valueOf(5.45), category));
		products.put(3L, new Product(3L, "Paints", BigDecimal.valueOf(3.0), category));
		products.put(4L, new Product(4L, "Red Pen", BigDecimal.valueOf(5.45), category));
		products.put(5L, new Product(5L, "Ruler", BigDecimal.valueOf(1.45), category));
	}

	@AfterEach
	public void reset() {
		cartService.clearCart(sessionId);
	}

	@Test
	public void getAndRemoveCartTest() {
		Assertions.assertNotNull(cartService.getCart(sessionId));

		Optional<CartDTO> removedCart = cartService.removeCart(sessionId);
		Assertions.assertTrue(removedCart.isPresent());
	}

	@Test
	public void addToCartTest() {
		Optional<Product> product = Optional.of(products.get(1L));
		Mockito.doReturn(product).when(productService).getById(1L);
		int count = 3;
		for (int i = 0; i < count; i++) {
			cartService.addToCart(sessionId, 1L);
		}
		BigDecimal sum = product.map(item -> item.getPrice().multiply(BigDecimal.valueOf(count)))
				.orElse(BigDecimal.ZERO);

		Mockito.verify(productService, Mockito.times(1)).getById(ArgumentMatchers.eq(1L));
		CartDTO cart = cartService.getCart(sessionId);
		Assertions.assertFalse(cart.isEmpty());
		Assertions.assertEquals(1, cart.getItems().size());
		Assertions.assertEquals(3, cart.getItems().get(0).getCount());
		Assertions.assertEquals(sum, cart.getSumTotal());
	}

	@Test
	public void removeFromCartTest() {
		Optional<Product> product = Optional.of(products.get(1L));
		Mockito.doReturn(product).when(productService).getById(1L);
		for (int i = 0; i < 3; i++) {
			cartService.addToCart(sessionId, 1L);
		}
		cartService.removeFromCart(sessionId, 1L);
		CartDTO cart = cartService.getCart(sessionId);
		Assertions.assertEquals(2, cart.getItems().get(0).getCount());
	}

	@Test
	public void deleteFromCartTest() {
		products.forEach((id, product) -> {
			Mockito.doReturn(Optional.of(product)).when(productService).getById(id);
			cartService.addToCart(sessionId, id);
		});
		cartService.deleteFromCart(sessionId, 1L);
		CartDTO cart = cartService.getCart(sessionId);
		Assertions.assertEquals(products.size() - 1, cart.getItems().size());
	}

	@Test
	public void mergeCartsTest() {
		CartDTO testCart = cartService.getCart(UUID.randomUUID());
		products.forEach((id, product) -> {
			Mockito.doReturn(Optional.of(product)).when(productService).getById(id);
			testCart.addItem(PRODUCT_TO_DTO_FACTORY.apply(product));
			cartService.addToCart(sessionId, id);
		});
		cartService.mergeCarts(sessionId, testCart);
		CartDTO cart = cartService.getCart(sessionId);
		Assertions.assertEquals(testCart.getItems().size(), cart.getItems().size());
		Assertions.assertEquals(testCart.getSumTotal().multiply(BigDecimal.valueOf(2)), cart.getSumTotal());
	}
}
