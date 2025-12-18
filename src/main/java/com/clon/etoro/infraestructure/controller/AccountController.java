package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.usecase.*;
import com.clon.etoro.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final GetAllAccountUseCase getAllAccountUseCase;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    // ✔ Coincide con POST /accounts/create
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> create(@RequestBody Account account) {
        return createAccountUseCase.execute(account);
    }

    // ✔ Coincide con PUT /accounts/update
    @PutMapping("/update")
    public Mono<Account> update(@RequestBody Account account) {
        return updateAccountUseCase.execute(account);
    }

    // ✔ GET /accounts
    @GetMapping
    public Flux<Account> getAll() {
        return getAllAccountUseCase.execute();
    }

    // ✔ GET /accounts/{id}
    @GetMapping("/{id}")
    public Mono<Account> getById(@PathVariable Long id) {
        return getAccountByIdUseCase.execute(id);
    }

    // ✔ DELETE /accounts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteAccountUseCase.execute(id);
    }
}
