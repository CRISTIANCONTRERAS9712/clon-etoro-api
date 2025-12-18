package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.request.CreateAccountRequest;
import com.clon.etoro.application.request.UpdateAccountRequest;
import com.clon.etoro.application.usecase.*;
import com.clon.etoro.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<Account> create(@RequestBody CreateAccountRequest request) {
        return createAccountUseCase.execute(request);
    }

    // ✔ Coincide con PUT /accounts/update
    @PutMapping("/update")
    public Mono<Account> update(@RequestBody UpdateAccountRequest request) {
        return updateAccountUseCase.execute(request);
    }

    // ✔ GET /accounts
    @GetMapping
    public Flux<Account> getAll() {
        return getAllAccountUseCase.execute();
    }

    // ✔ GET /accounts/{id}
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Account>> getById(@PathVariable Long id) {
        return getAccountByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .onErrorResume(
                        RuntimeException.class,
                        e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    // ✔ DELETE /accounts/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return deleteAccountUseCase.execute(id);
    }
}
