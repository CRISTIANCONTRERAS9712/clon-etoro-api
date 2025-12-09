package com.clon.etoro.infraestructure.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clon.etoro.application.usecase.CreateUserRequest;
import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.domain.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

	private final CreateUserUseCase useCase;
	private final GetAllUserUseCase getAllUsersUseCase;

	public UserController(CreateUserUseCase useCase, GetAllUserUseCase getAllUsersUseCase) {
		this.useCase = useCase;
		this.getAllUsersUseCase = getAllUsersUseCase;
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
	public ResponseEntity<User> create(@RequestBody CreateUserRequest body) {
		User user = useCase.execute(body);
		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users =getAllUsersUseCase.execute();
		return ResponseEntity.ok(users);
	}
}
