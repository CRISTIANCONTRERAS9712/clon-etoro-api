package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.UserEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataUserRepository extends ReactiveCrudRepository<UserEntity, Long> {

	Mono<Boolean> existsByEmail(String email);

	Mono<UserEntity> findByEmail(String email);
}
