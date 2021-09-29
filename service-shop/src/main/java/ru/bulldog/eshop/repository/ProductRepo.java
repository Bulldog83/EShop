package ru.bulldog.eshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	@Override
	@EntityGraph(value = "Product.withData")
	Page<Product> findAll(Specification<Product> spec, Pageable pageable);
	@Override
	@EntityGraph(value = "Product.withData")
	Optional<Product> findById(Long id);
}
