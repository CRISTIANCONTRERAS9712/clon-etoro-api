package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetAccountByIdUseCase {

    private final AccountRepositoryPort repository;

    public Mono<Account> execute(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")));
    }
}
