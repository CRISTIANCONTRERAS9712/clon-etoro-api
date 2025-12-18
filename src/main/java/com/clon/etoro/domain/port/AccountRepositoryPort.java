package com.clon.etoro.domain.port;

import com.clon.etoro.domain.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepositoryPort {

	Mono<Account> save(Account u);

	Mono<Account> update(Account u);

	Flux<Account> findAll();

	Mono<Account> findById(Long id);

	Mono<Void> deleteById(Long id);

	Mono<Account> findByUserId(Long userId);
}
