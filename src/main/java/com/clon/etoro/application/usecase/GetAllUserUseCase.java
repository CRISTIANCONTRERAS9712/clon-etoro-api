package com.clon.etoro.application.usecase;

import java.util.List;

import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepository;

public class GetAllUserUseCase {

	private final UserRepository repo;

    public GetAllUserUseCase(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> execute() {
        return repo.findAll();
    }
}
