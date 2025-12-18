package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllAccountUseCase {

    private final AccountRepositoryPort repository;

    public Flux<Account> execute() {
        return repository.findAll();
    }
}
