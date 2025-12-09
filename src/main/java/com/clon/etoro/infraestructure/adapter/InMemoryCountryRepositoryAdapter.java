package com.clon.etoro.infraestructure.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepository;
import com.clon.etoro.domain.port.UserRepository;

public class InMemoryCountryRepositoryAdapter implements CountryRepository {
	
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
	public Optional<Country> getCountryByCodeIso(String isoCode) {
		return countries.stream().filter(country -> country.getIsoCountry().equals(isoCode)).findAny();
	}

}
