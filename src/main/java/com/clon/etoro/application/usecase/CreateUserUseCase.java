package com.clon.etoro.application.usecase;

import com.clon.etoro.application.request.CreateUserRequest;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;

import reactor.core.publisher.Mono;

//Caso de Uso (Puro)
public class CreateUserUseCase {

	private final UserRepositoryPort userRepo;
	private final CountryRepositoryPort countryRepo;
	private final UserDomainService service;

	public CreateUserUseCase(UserRepositoryPort userRepo, UserDomainService service,
			CountryRepositoryPort countryRepo) {
		this.userRepo = userRepo;
		this.service = service;
		this.countryRepo = countryRepo;
	}

	public Mono<User> execute(CreateUserRequest createUser) {
		// Lógica de negocio pura

		System.out.println("asdasd: " + createUser.toString());
		User u = new User(
				null,
				createUser.firstname(),
				createUser.lastname(),
				createUser.email(),
				createUser.birthday(),
				createUser.password(),
				createUser.cellphone(),
				new Country(createUser.isoCountry()));
		System.out.println("asdasd: " + u.toString());
		return service.validateUserCreation(u);
	}
}
