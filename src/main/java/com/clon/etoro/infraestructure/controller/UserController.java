package com.clon.etoro.infraestructure.controller;

import java.time.LocalDate;
import java.util.List;

import com.clon.etoro.application.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clon.etoro.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final CreateUserUseCase createUserUseCase;
	private final GetAllUserUseCase getAllUsersUseCase;
	private final UpdateUserUseCase updateUserUseCase;
	private final GetByIdUserUseCase getByIdUserUseCase;

	/***
	 * 
	 * { "firstname": "Mateo", "lastname": "Corredor", "email": "m2@gmail.com",
	 * "birthday": "2000-12-12", "password": "12345678", "cellphone": "3111234578",
	 * "isoCountry": "MX" }
	 * 
	 * @param body
	 * @return
	 */
	@PostMapping("/create")
	public Mono<ResponseEntity<User>> create(@RequestBody CreateUserRequest body) {
		// User user = useCase.execute(body);
		// return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(user);
		return createUserUseCase.execute(body)
				.map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser));

		// .onErrorResume(RuntimeException.class, e ->
		// Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build())
		// );
	}

	@PutMapping("/update")
	public Mono<ResponseEntity<User>> update(@RequestBody UpdateUserRequest request) {
		return updateUserUseCase.execute(request).map(ResponseEntity::ok);
	}

	@GetMapping("/hello")
	public Mono<ResponseEntity<String>> hello() {
		return Mono.just(ResponseEntity.ok("Hello netty!"));
	}

	@GetMapping("/{idUser}")
	public Mono<ResponseEntity<User>> getById(@PathVariable("idUser") Long idUser) {
		return getByIdUserUseCase.execute(idUser)
				.map(ResponseEntity::ok)
				.onErrorResume(
						RuntimeException.class,
						e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
				);
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<User>>> getAllUsers() {
		Flux<User> users = getAllUsersUseCase.execute();
		return Mono.just(ResponseEntity.ok(users));
	}
}
