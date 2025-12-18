package com.clon.etoro.application.usecase;

import com.clon.etoro.application.request.CreateCountryRequest;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;

import reactor.core.publisher.Mono;

//Caso de Uso (Puro)
public class CreateCountryUseCase {

	private final CountryRepositoryPort countryRepo;

	public CreateCountryUseCase(CountryRepositoryPort countryRepo) {
		this.countryRepo = countryRepo;
	}

	public Mono<Country> execute(CreateCountryRequest createCountry) {
		// Lógica de negocio pura
		Country c = new Country(
				null,
				createCountry.isoCountry(),
				createCountry.name(),
				createCountry.active());
		return countryRepo.save(c);
	}
}
