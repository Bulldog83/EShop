package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	@Override
	@EntityGraph(value = "Order.withItems")
	Optional<Order> findById(Long id);
	@EntityGraph(value = "Order.withItems")
	List<Order> findBySessionId(UUID session);
}
