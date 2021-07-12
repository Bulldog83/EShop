package ru.bulldog.eshop.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bulldog.eshop.model.Product;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductRepo implements EntityRepo<Product> {

	private final static Logger logger = LogManager.getLogger(ProductRepo.class);

	private final SessionFactory sessionFactory;

	@Autowired
	public ProductRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@PostConstruct
	private void init() {
		try (Session session = sessionFactory.getCurrentSession()) {
			String schemaSql = Files.lines(Paths.get(getClass().getResource("/schema.sql").toURI())).collect(Collectors.joining(" "));
			String dataSql = Files.lines(Paths.get(getClass().getResource("/data.sql").toURI())).collect(Collectors.joining(" "));
			session.beginTransaction();
			session.createNativeQuery(schemaSql).executeUpdate();
			session.createNativeQuery(dataSql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception ex) {
			logger.error("Database init error", ex);
		}
	}

	@Override
	public Optional<Product> getOne(long id) {
		try (Session session = sessionFactory.getCurrentSession()) {
			session.beginTransaction();
			Product product = session.get(Product.class, id);
			session.getTransaction().commit();
			return Optional.of(product);
		} catch (Exception ex) {
			return Optional.empty();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		try (Session session = sessionFactory.getCurrentSession()) {
			session.beginTransaction();
			List<Product> products = (List<Product>) session.createQuery("from Product").getResultList();
			session.getTransaction().commit();
			return products;
		} catch (Exception ex) {
			logger.warn("Data base error", ex);
			return new ArrayList<>();
		}
	}

	public Product save(Product product) {
		try (Session session = sessionFactory.getCurrentSession()) {
			session.beginTransaction();
			session.saveOrUpdate(product);
			session.getTransaction().commit();
		}
		return product;
	}

	public void delete(Product product) {
		try (Session session = sessionFactory.getCurrentSession()) {
			session.beginTransaction();
			session.delete(product);
			session.getTransaction().commit();
		}
	}
}
