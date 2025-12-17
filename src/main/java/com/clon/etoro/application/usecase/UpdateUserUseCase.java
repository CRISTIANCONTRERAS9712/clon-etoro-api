package com.clon.etoro.application.usecase;

import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepositoryPort userRepo;
    private final CountryRepositoryPort countryRepo;
    private final UserDomainService userDomainService;

    public Mono<User> execute(UpdateUserRequest request) {

        // 1️⃣ Buscar usuario
        Mono<User> userMono = userRepo.findById(request.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));

        // 2️⃣ Resolver país solo si viene en el request
        Mono<Country> countryMono = userDomainService.resolveCountryIfProvided(request.getIsoCountry());

        // 3️⃣ Combinar usuario + país opcional y aplicar reglas
        return userMono
                .zipWith(countryMono)
                .flatMap(tuple -> {
                    User user = tuple.getT1();
                    Country country = tuple.getT2();
                    return userDomainService.applyUserUpdate(user, country);
                })
                // 4️⃣ Guardar usuario final
                .flatMap(userRepo::save)

                // 5️⃣ Consultar nuevamente el país completo ANTES de regresarlo al controller
                .flatMap(savedUser -> {

                    Long idCountry = savedUser.getCountry().getId();

                    return countryRepo.getCountryById(idCountry)
                            .map(fullCountry -> {
                                savedUser.setCountry(fullCountry); // completar campos
                                return savedUser;
                            })
                            .switchIfEmpty(Mono.just(savedUser)); // fallback improbable
                });

    }
}
