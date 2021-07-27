package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
