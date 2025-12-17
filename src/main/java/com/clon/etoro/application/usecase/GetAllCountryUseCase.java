package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.port.CountryRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllCountryUseCase {

	private final CountryRepositoryPort countryRepositoryPort;

	public Flux<Country> execute() {
		return countryRepositoryPort.findAll();
	}
}
