package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.PositionEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataPositionRepository extends ReactiveCrudRepository<PositionEntity, Long> {

	Mono<PositionEntity> findByUserId(Long userId);

	Mono<PositionEntity> findByAssetId(Long assetId);

	Mono<PositionEntity> findByUserIdAndAssetId(Long userId, Long assetId);
}
