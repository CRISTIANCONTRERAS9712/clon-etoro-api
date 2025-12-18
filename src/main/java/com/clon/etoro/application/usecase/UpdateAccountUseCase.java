package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final AccountRepositoryPort repository;

    public Mono<Account> execute(Account account) {
        return repository.update(account);
    }
}
