package com.clon.etoro.infraestructure.controller;

import java.time.LocalDate;
import java.util.List;

import com.clon.etoro.application.usecase.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clon.etoro.domain.model.User;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private final CreateUserUseCase createUserUseCase;
	private final GetAllUserUseCase getAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

	public UserController(CreateUserUseCase createUserUseCase, GetAllUserUseCase getAllUsersUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase) {
		this.createUserUseCase = createUserUseCase;
		this.getAllUsersUseCase = getAllUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
	}

	/***
	 * 
	 * {
		    "firstname": "Mateo",
		    "lastname": "Corredor",
		    "email": "m2@gmail.com",
		    "birthday": "2000-12-12",
		    "password": "12345678",
		    "cellphone": "3111234578",
		    "isoCountry": "MX"
		}
	 * @param body
	 * @return
	 */
	@PostMapping("/create")
	public Mono<ResponseEntity<User>> create(@RequestBody Mono<CreateUserRequest> body) {
//		User user = useCase.execute(body);
//		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(user);
		return body
				.flatMap(createUserUseCase::execute)
				.map(createdUser -> ResponseEntity.status(HttpStatus.CREATED).body(createdUser));
//				.onErrorResume(RuntimeException.class, e ->
//					Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build())
//				);
		
	}

    @PutMapping("/update")
    public Mono<ResponseEntity<User>> update(
            @RequestBody UpdateUserRequest request) {

        return updateUserUseCase.execute(request)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<String>> delete(@RequestBody DeleteUserRequest request) {
        return deleteUserUseCase.execute(request.getId())
                .thenReturn(ResponseEntity.ok("Usuario eliminado correctamente"));
    }



    @GetMapping("/hello")
	public Mono<ResponseEntity<String>> hello() {
		return Mono.just(ResponseEntity.ok("Hello netty!"));
	}
	
	@GetMapping
	public Mono<ResponseEntity<Flux<User>>> getAllUsers() {
		Flux<User> users = getAllUsersUseCase.execute();
		return Mono.just(ResponseEntity.ok(users));
	}
}
