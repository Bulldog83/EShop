package ru.bulldog.eshop.service;

import ru.bulldog.eshop.model.Address;

import java.util.Optional;

public interface AddressService {
	Optional<Address> getById(Long id);
	Address create(Address address);
	Address save(Address address);
}
