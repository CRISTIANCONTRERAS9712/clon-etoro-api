package com.clon.etoro.domain.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import com.clon.etoro.application.usecase.UpdateUserRequest;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import reactor.core.publisher.Mono;

public class UserDomainService {
	private final UserRepositoryPort userRepositoryPort;
    private final CountryRepositoryPort countryRepositoryPort;

	public UserDomainService(
			UserRepositoryPort userRepositoryPort,
			CountryRepositoryPort countryRepositoryPort
	) {
		this.userRepositoryPort = userRepositoryPort;
        this.countryRepositoryPort = countryRepositoryPort;
	}

	public Mono<User> validateUserCreation(User user) {
		//Validación 1: Mayor de 18 años (Logica de Dominio Pura/Síncronica)
		if (!isUserAdult(user.getBirthdate())){
			return Mono.error(new RuntimeException("El usuario debe ser mayor de 18 años"));
		}

		//Validación 2: (Lógica Asíncrona/Reactiva)
		return countryRepositoryPort.getCountryByCodeIso(user.getCountry().getIsoCountry())
				.doOnNext(country -> System.out.println("DEBUG MANUAL: Resultado de getCountryByCodeIso: " + country.getIdCountry()))
				.switchIfEmpty(Mono.error(new RuntimeException("Country no encontrado"))) // Si no encuentra el país, lanza error aquí.
				.flatMap(countryFound -> {
					if(!countryFound.getActive()) {
						return Mono.error(new RuntimeException("Country no activo"));
					}
					user.setCountry(countryFound);
		            return Mono.just(user);
				})
				// Validación 3: Asíncrona (Email existe)
	            .flatMap(validateUser -> userRepositoryPort.existsByEmail(validateUser.getEmail()))
	            .flatMap(emailExists -> {
	                if(emailExists) {
	                    return Mono.error(new RuntimeException("El correo ya está registrado"));
	                }
	                // Si pasa todas las validaciones, guardamos
	                return userRepositoryPort.save(user);
	            });
		
	}

    // ----------------------------------------
    // 1️⃣ Resolver país solo si viene en request
    // ----------------------------------------
    public Mono<Country> resolveCountryIfProvided(UpdateUserRequest request) {
        return Optional.ofNullable(request.getIsoCountry())
                .map(iso -> countryRepositoryPort.getCountryByCodeIso(iso)
                        .switchIfEmpty(Mono.error(new RuntimeException("Country not found")))
                        .flatMap(country -> {
                            if (!country.getActive()) {
                                return Mono.error(new RuntimeException("Country not active"));
                            }
                            return Mono.just(country);
                        })
                )
                .orElse(Mono.empty());
    }


    // ----------------------------------------
    // 2️⃣ Reglas de negocio para actualizar usuario
    // ----------------------------------------
    public Mono<User> applyUserUpdate(User user, UpdateUserRequest request, Country country) {

        // Validación edad si se quiere cambiar
        if (request.getBirthdate() != null && !isUserAdult(request.getBirthdate())) {
            return Mono.error(new RuntimeException("El usuario debe ser mayor de 18 años"));
        }

        // Si viene país, actualizarlo
        if (country != null) {
            user.setCountry(country);
        }

        // Actualizar campos si vienen
        Optional.ofNullable(request.getFirstname()).ifPresent(user::setFirstname);
        Optional.ofNullable(request.getLastname()).ifPresent(user::setLastname);
        Optional.ofNullable(request.getCellphone()).ifPresent(user::setCellphone);
        Optional.ofNullable(request.getPassword()).ifPresent(user::setPassword);
        Optional.ofNullable(request.getBirthdate()).ifPresent(user::setBirthdate);

        return Mono.just(user);
    }

	private Boolean isUserAdult(LocalDate birthDate) {
		if (birthDate == null)
			return Boolean.FALSE;
		return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
	}
}