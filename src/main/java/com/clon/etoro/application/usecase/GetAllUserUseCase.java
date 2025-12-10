package com.clon.etoro.application.usecase;

import java.util.List;

import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepositoryPort;

import reactor.core.publisher.Flux;

public class GetAllUserUseCase {

	private final UserRepositoryPort repo;

    public GetAllUserUseCase(UserRepositoryPort repo) {
        this.repo = repo;
    }

    public Flux<User> execute() {
        return repo.findAll();
    }
}
