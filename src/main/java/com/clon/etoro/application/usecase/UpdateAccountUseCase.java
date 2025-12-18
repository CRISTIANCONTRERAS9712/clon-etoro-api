package com.clon.etoro.application.usecase;

import java.util.Optional;

import com.clon.etoro.application.request.UpdateAccountRequest;
import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateAccountUseCase {

    private final AccountRepositoryPort accountRepository;
    private final UserRepositoryPort userRepository;

    public Mono<Account> execute(UpdateAccountRequest request) {

        return Mono.zip(
                userRepository.findById(request.userId())
                        .switchIfEmpty(Mono
                                .error(new RuntimeException("Usuario no encontrado"))),
                accountRepository.findById(request.accountId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada"))))
                .map(tuple -> {
                    User user = tuple.getT1();
                    Account account = tuple.getT2();

                    account.setUser(user);

                    Optional.ofNullable(request.cashAvailable()).ifPresent(account::setCashAvailable);

                    return account;
                })
                .flatMap(accountRepository::update);
    }
}
