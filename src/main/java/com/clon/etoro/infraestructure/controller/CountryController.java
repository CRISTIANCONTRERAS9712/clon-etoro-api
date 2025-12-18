package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.request.CreateCountryRequest;
import com.clon.etoro.application.request.UpdateCountryRequest;
import com.clon.etoro.application.usecase.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clon.etoro.domain.model.Country;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

	private final CreateCountryUseCase createCountryUseCase;
	private final UpdateCountryUseCase updateCountryUseCase;
	private final GetAllCountryUseCase getAllCountryUseCase;
	private final GetByIdCountryUseCase getByIdCountryUseCase;
	private final DeleteCountryUseCase deleteCountryUseCase;

	@PostMapping
	public Mono<ResponseEntity<Country>> create(@RequestBody CreateCountryRequest body) {
		return createCountryUseCase.execute(body)
				.map(createdCountry -> ResponseEntity.status(HttpStatus.CREATED).body(createdCountry))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Country>> update(@PathVariable Long id, @RequestBody UpdateCountryRequest body) {
		return updateCountryUseCase.execute(body)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<Country>>> getAll() {
		Flux<Country> countries = getAllCountryUseCase.execute();
		return Mono.just(ResponseEntity.ok(countries));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Country>> getById(@PathVariable Long id) {
		return getByIdCountryUseCase.execute(id)
				.map(ResponseEntity::ok)
				.onErrorResume(
						RuntimeException.class,
						e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
		return deleteCountryUseCase.execute(id)
				.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
