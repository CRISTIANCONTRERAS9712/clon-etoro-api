package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.AccountEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataAccountRepository extends ReactiveCrudRepository<AccountEntity, Long> {

	Mono<AccountEntity> findByUserId(Long userId);
}
