package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Country;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryRepositoryPort {

	Mono<Country> save(Country country);

	Mono<Country> update(Country country);

	Flux<Country> findAll();

	Mono<Country> findById(Long id);

	Mono<Void> deleteById(Long id);

	Mono<Boolean> isCountryActive(String isoCode);

	Mono<Country> getCountryByCodeIso(String isoCode);

	Mono<Country> getCountryById(Long id);
}
