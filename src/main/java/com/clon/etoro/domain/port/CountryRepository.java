package com.clon.etoro.domain.port;

import java.util.List;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;

public interface CountryRepository {
	boolean isCountryActive(String isoCode);
	Optional<Country> getCountryByCodeIso(String isoCode);
}
