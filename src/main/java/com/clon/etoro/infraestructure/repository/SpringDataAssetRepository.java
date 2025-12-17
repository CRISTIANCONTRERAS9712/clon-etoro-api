package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.AssetEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataAssetRepository extends ReactiveCrudRepository<AssetEntity, Long> {

	Mono<AssetEntity> findByTicker(String ticker);
}
