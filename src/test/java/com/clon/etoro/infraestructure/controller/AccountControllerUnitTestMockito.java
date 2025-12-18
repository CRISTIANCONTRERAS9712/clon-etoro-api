package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.request.CreateAccountRequest;
import com.clon.etoro.application.request.UpdateAccountRequest;
import com.clon.etoro.application.usecase.*;
import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@WebFluxTest(AccountController.class)
public class AccountControllerUnitTestMockito {

    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    private CreateAccountUseCase createAccountUseCase;

    @MockitoBean
    private UpdateAccountUseCase updateAccountUseCase;

    @MockitoBean
    private GetAllAccountUseCase getAllAccountUseCase;

    @MockitoBean
    private GetAccountByIdUseCase getAccountByIdUseCase;

    @MockitoBean
    private DeleteAccountUseCase deleteAccountUseCase;

    // ------------------ TEST DATA ------------------

    private Account defaultAccount;
    private User defaultUser;

    @BeforeEach
    void setUp() {
        defaultUser = new User(1L);

        defaultAccount = new Account();
        defaultAccount.setId(1L);
        defaultAccount.setCashAvailable(new BigDecimal("1000.00").doubleValue());
        defaultAccount.setUser(defaultUser);
    }

    // ------------------ TESTS ------------------

    @Test
    void testCreateAccount_ShouldReturnCreatedAccount() {

        CreateAccountRequest request = new CreateAccountRequest(
                1L,
                new BigDecimal("1000.00")
        );

        Account account = new Account(
                1L,
                new User(1L),
                1000.0
        );

        Mockito.when(createAccountUseCase.execute(Mockito.any(Account.class)))
                .thenReturn(Mono.just(account));

        webClient.post()
                .uri("/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Account.class)
                .isEqualTo(account);
    }

    @Test
    void testUpdateAccount_ShouldReturnUpdatedAccount() {
        UpdateAccountRequest request = new UpdateAccountRequest(
                1L,
                new BigDecimal("2000.00")
        );

        Account updatedAccount = new Account();
        updatedAccount.setId(1L);
        updatedAccount.setCashAvailable(new BigDecimal("2000.00").doubleValue());
        updatedAccount.setUser(defaultUser);

        Mockito.when(updateAccountUseCase.execute(Mockito.any(Account.class)))
                .thenReturn(Mono.just(updatedAccount));

        webClient.put()
                .uri("/accounts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .isEqualTo(updatedAccount);
    }

    @Test
    void testGetAccountById_ShouldReturnAccount() {
        Long accountId = 1L;

        Mockito.when(getAccountByIdUseCase.execute(accountId))
                .thenReturn(Mono.just(defaultAccount));

        webClient.get()
                .uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .isEqualTo(defaultAccount);
    }

    @Test
    void testGetAccountById_ShouldReturnNotFound() {
        Long nonExistentId = 2L;

        Mockito.when(getAccountByIdUseCase.execute(nonExistentId))
                .thenReturn(Mono.error(new RuntimeException("Account not found")));

        webClient.get()
                .uri("/accounts/{id}", nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAllAccounts_ShouldReturnAccountList() {
        Account account2 = new Account();
        account2.setId(2L);
        account2.setCashAvailable(new BigDecimal("500.00").doubleValue());
        account2.setUser(new User(2L));

        List<Account> accountList = Arrays.asList(defaultAccount, account2);

        Mockito.when(getAllAccountUseCase.execute())
                .thenReturn(Flux.fromIterable(accountList));

        webClient.get()
                .uri("/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .isEqualTo(accountList);
    }

    @Test
    void testDeleteAccount_ShouldReturnNoContent() {
        Long accountId = 1L;

        Mockito.when(deleteAccountUseCase.execute(accountId))
                .thenReturn(Mono.empty());

        webClient.delete()
                .uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
