package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Propertie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PropertieRepositoryPort {

	Mono<Propertie> save(Propertie u);

	Mono<Propertie> update(Propertie u);

	Flux<Propertie> findAll();

	Mono<Propertie> findById(Long id);

	Mono<Void> deleteById(Long id);

	Mono<Propertie> findByKey(String key);
}
