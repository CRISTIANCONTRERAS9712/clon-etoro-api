package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.port.UserRepositoryPort;
import reactor.core.publisher.Mono;

public class DeleteUserUseCase {

    private final UserRepositoryPort userRepository;

    public DeleteUserUseCase(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Void> execute(Long id) {

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .then(userRepository.deleteById(id));
    }
}
