package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AssetRepositoryPort {

	Mono<Asset> save(Asset u);

	Mono<Asset> update(Asset u);

	Flux<Asset> findAll();

	Mono<Asset> findById(Long id);

	Mono<Void> deleteById(Long id);

	Mono<Asset> findByTicker(String ticker);
}
