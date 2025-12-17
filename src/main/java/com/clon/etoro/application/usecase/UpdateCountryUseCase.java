package com.clon.etoro.application.usecase;

import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateCountryUseCase {

    private final CountryRepositoryPort countryRepo;

    public Mono<Country> execute(UpdateCountryRequest request) {

        // 1️⃣ Buscar country
        Mono<Country> countryMono = countryRepo.findById(request.id())
                .switchIfEmpty(Mono.error(new RuntimeException("Country not found")));

        // 2️⃣ Combinar usuario + país opcional y aplicar reglas
        return countryMono
                .flatMap(country -> {

                    Optional.ofNullable(request.iso()).ifPresent(country::setIso);
                    Optional.ofNullable(request.name()).ifPresent(country::setName);
                    Optional.ofNullable(request.active()).ifPresent(country::setActive);

                    return countryRepo.update(country);
                })
                // 3️⃣ Guardar country final
                .flatMap(countryRepo::save);

    }
}
