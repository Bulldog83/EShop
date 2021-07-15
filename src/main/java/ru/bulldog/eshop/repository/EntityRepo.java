package ru.bulldog.eshop.repository;

import java.util.List;
import java.util.Optional;

public interface EntityRepo<T> {
	Optional<T> getOne(long id);
	List<T> findAll();
}
