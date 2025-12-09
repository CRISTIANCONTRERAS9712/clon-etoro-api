package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepository;
import com.clon.etoro.domain.port.UserRepository;
import com.clon.etoro.domain.service.UserDomainService;

public class CreateUserUseCase {

	private final UserRepository userRepo;
	private final CountryRepository countryRepo;
	private final UserDomainService service;

	public CreateUserUseCase(UserRepository userRepo, UserDomainService service, CountryRepository countryRepo) {
		this.userRepo = userRepo;
		this.service = service;
		this.countryRepo = countryRepo;
	}

	public User execute(CreateUserRequest createUser) {
		Country country = countryRepo.getCountryByCodeIso(createUser.isoCountry())
				.orElseThrow(() -> new RuntimeException("Country invalido, no existe"));
		User u = new User(null, createUser.firstname(), createUser.lastname(), createUser.email(),
				createUser.birthday(), createUser.password(), createUser.cellphone(), country);
		service.validateUserCreation(u);
		return userRepo.save(u);
	}
}
