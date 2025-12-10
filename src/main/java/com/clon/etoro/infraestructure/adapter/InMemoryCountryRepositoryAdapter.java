package com.clon.etoro.infraestructure.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import reactor.core.publisher.Mono;

public class InMemoryCountryRepositoryAdapter implements CountryRepositoryPort {
	
private final List<Country> countries = new ArrayList<>();
	
    public InMemoryCountryRepositoryAdapter() {
    	countries.add(new Country(1L, "CO", "Colombia", Boolean.TRUE));
    	countries.add(new Country(2L, "US", "Estados Unidos", Boolean.TRUE));
    	countries.add(new Country(3L, "MX", "Mexico", Boolean.FALSE));
    }

	@Override
	public boolean isCountryActive(String isoCode) {
		return countries.stream().anyMatch(country -> country.getIsoCountry().equals(isoCode) && country.getActive());
	}

	@Override
	public Mono<Country> getCountryByCodeIso(String isoCode) {
		Optional<Country> foundCountry = countries.stream()
				.filter(country -> country.getIsoCountry().equals(isoCode))
				.findFirst();
		return Mono.justOrEmpty(foundCountry);
//		return countries.stream().filter(country -> country.getIsoCountry().equals(isoCode)).findAny();
	}

}
