package com.clon.etoro.infraestructure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.port.CountryRepositoryPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class InMemoryCountryRepositoryAdapter implements CountryRepositoryPort {

	private final List<Country> countries = new ArrayList<>();

	public InMemoryCountryRepositoryAdapter() {
		countries.add(new Country(1L, "CO", "Colombiaaaa", Boolean.TRUE));
		countries.add(new Country(2L, "US", "Estados Unidossss", Boolean.TRUE));
		countries.add(new Country(3L, "MX", "Mexicoooo", Boolean.FALSE));
	}

	@Override
	public Mono<Boolean> isCountryActive(String isoCode) {
		Boolean isActive = countries.stream()
				.anyMatch(country -> country.getIso().equals(isoCode) && country.getActive());
		return Mono.just(isActive);
	}

	@Override
	public Mono<Country> getCountryByCodeIso(String isoCode) {
		Optional<Country> foundCountry = countries.stream()
				.filter(country -> country.getIso().equals(isoCode))
				.findFirst();
		return Mono.justOrEmpty(foundCountry);
		// return countries.stream().filter(country ->
		// country.getIsoCountry().equals(isoCode)).findAny();
	}

	@Override
	public Mono<Country> getCountryById(Long id) {
		Optional<Country> foundCountry = countries.stream()
				.filter(country -> country.getId().equals(id))
				.findFirst();
		return Mono.justOrEmpty(foundCountry);
	}

	@Override
	public Mono<Country> save(Country country) {
		countries.add(country);
		return Mono.just(country);
	}

	@Override
	public Mono<Country> update(Country country) {
		countries.removeIf(c -> c.getId().equals(country.getId()));
		countries.add(country);
		return Mono.just(country);
	}

	@Override
	public Flux<Country> findAll() {
		return Flux.fromIterable(countries);
	}

	@Override
	public Mono<Country> findById(Long id) {
		Optional<Country> foundCountry = countries.stream()
				.filter(country -> country.getId().equals(id))
				.findFirst();
		return Mono.justOrEmpty(foundCountry);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		countries.removeIf(c -> c.getId().equals(id));
		return Mono.empty();
	}

}
