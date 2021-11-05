package ru.bulldog.eshop.service.impl;

import org.springframework.stereotype.Service;
import ru.bulldog.eshop.model.Address;
import ru.bulldog.eshop.repository.AddressRepo;
import ru.bulldog.eshop.service.AddressService;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

	private final AddressRepo repository;

	public AddressServiceImpl(AddressRepo repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Address> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Address create(Address address) {
		return repository.save(address);
	}

	@Override
	public Address save(Address address) {
		return repository.save(address);
	}
}
