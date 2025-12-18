package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllAccountUseCase {

    private final AccountRepositoryPort repository;
    private final UserRepositoryPort userRepository;

    public Flux<Account> execute() {
        return repository.findAll()
                .flatMap(account -> {
                    return userRepository.findById(account.getUser().getId())
                            .map(user -> {
                                account.setUser(user);
                                return account;
                            });
                });
    }
}
