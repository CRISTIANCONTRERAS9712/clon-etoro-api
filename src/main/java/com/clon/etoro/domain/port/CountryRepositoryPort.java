package com.clon.etoro.domain.port;

import java.util.List;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;

import reactor.core.publisher.Mono;

public interface CountryRepositoryPort {
	boolean isCountryActive(String isoCode);
	Mono<Country> getCountryByCodeIso(String isoCode);
}
