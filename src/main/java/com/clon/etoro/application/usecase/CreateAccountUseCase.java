package com.clon.etoro.application.usecase;

import com.clon.etoro.application.request.CreateAccountRequest;
import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepositoryPort accountRepository;
    private final UserRepositoryPort userRepository;

    public Mono<Account> execute(CreateAccountRequest request) {
        return userRepository.findById(request.userId())
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")))
                .flatMap(user -> {
                    Account account = new Account();
                    account.setUser(user);
                    account.setCashAvailable(request.cashAvailable().doubleValue());

                    return accountRepository.save(account)
                            .map(savedAccount -> Tuples.of(savedAccount, user));
                })
                .map(tuple -> {
                    Account savedAccount = tuple.getT1();
                    User user = tuple.getT2();
                    savedAccount.setUser(user);
                    return savedAccount;
                });
    }
}
