package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Optional<Category> findByTitle(String title);
}
