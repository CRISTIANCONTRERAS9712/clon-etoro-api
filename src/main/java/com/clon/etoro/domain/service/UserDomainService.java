package com.clon.etoro.domain.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.extern.slf4j.Slf4j;
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
				.doOnNext(country -> System.out.println("DEBUG MANUAL: Resultado de getCountryByCodeIso: " + country))
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

	private Boolean isUserAdult(LocalDate birthDate) {
		if (birthDate == null)
			return Boolean.FALSE;
		return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
	}
}