package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.port.CountryRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetByIdCountryUseCase {

	private final CountryRepositoryPort countryRepositoryPort;

	public Mono<Country> execute(Long id) {
		return countryRepositoryPort.findById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Pais no encontrado")));
	}
}
