package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Position;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PositionRepositoryPort {

	Mono<Position> save(Position u);

	Mono<Position> update(Position u);

	Flux<Position> findAll();

	Mono<Position> findById(Long id);

	Mono<Void> deleteById(Long id);

	Mono<Position> findByUserId(Long userId);

	Mono<Position> findByAssetId(Long assetId);

	Mono<Position> findByUserIdAndAssetId(Long userId, Long assetId);
}
