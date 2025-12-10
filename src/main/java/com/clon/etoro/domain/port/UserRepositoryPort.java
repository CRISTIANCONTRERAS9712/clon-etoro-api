package com.clon.etoro.domain.port;

import java.util.List;
import java.util.Optional;

import com.clon.etoro.domain.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryPort {
	
	Mono<Boolean> existsByEmail(String email);
	Mono<User> save(User u);
	Mono<User> update(User u);
	Mono<User> getByEmail(String email);
	Flux<User> findAll();
}
