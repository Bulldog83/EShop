package ru.bulldog.eshop.repository;

import ru.bulldog.eshop.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
	@Override
	@EntityGraph(value = "Order.withData")
	Optional<Order> findById(Long id);
	@EntityGraph(value = "Order.withData")
	List<Order> findBySessionId(UUID session);
}
