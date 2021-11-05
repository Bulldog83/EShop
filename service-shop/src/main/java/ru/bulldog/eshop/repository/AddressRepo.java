package ru.bulldog.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bulldog.eshop.model.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {}
