package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetAllUserUseCase {

	private final UserRepositoryPort repo;
	private final CountryRepositoryPort countryRepositoryPort;

	public Flux<User> execute() {
		return repo.findAll() // Esto devuelve Flux<User> (usuarios con Country incompleto)
				.flatMap(user -> {
					// countryRepositoryPort.getCountryByCodeIso devuelve Mono<Country>
					Mono<Country> countryMono = countryRepositoryPort
							.getCountryById(user.getCountry().getId());

					// Usamos un segundo flatMap dentro para esperar el Mono<Country>
					// y luego combinarlo con el User original para devolver un Mono<User>
					return countryMono.map(foundCountry -> {
						// Asigna el objeto Country completo al User
						user.setCountry(foundCountry);
						return user; // Devuelve el User modificado
					});
				});
	}
}
