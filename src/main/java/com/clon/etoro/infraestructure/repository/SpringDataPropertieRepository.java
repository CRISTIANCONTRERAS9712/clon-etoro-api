package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.PropertieEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataPropertieRepository extends ReactiveCrudRepository<PropertieEntity, Long> {

	Mono<PropertieEntity> findByKey(String key);
}
