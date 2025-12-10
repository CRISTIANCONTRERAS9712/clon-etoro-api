package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Country;
import reactor.core.publisher.Mono;

public interface CountryRepositoryPort {
	
	Mono<Boolean> isCountryActive(String isoCode);
	Mono<Country> getCountryByCodeIso(String isoCode);
	Mono<Country> getCountryById(Long id);
}
