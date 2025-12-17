package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.port.CountryRepositoryPort;
import reactor.core.publisher.Mono;

public class DeleteCountryUseCase {

    private final CountryRepositoryPort countryRepository;

    public DeleteCountryUseCase(CountryRepositoryPort countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Mono<Void> execute(Long id) {

        return countryRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Pais no encontrado")))
                .then(countryRepository.deleteById(id));
    }
}
