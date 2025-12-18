package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.request.CreatePositionRequest;
import com.clon.etoro.application.request.UpdatePositionRequest;
import com.clon.etoro.application.usecase.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clon.etoro.domain.model.Position;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

	private final CreatePositionUseCase createPositionUseCase;
	private final UpdatePositionUseCase updatePositionUseCase;
	private final GetAllPositionUseCase getAllPositionUseCase;
	private final GetByIdPositionUseCase getByIdPositionUseCase;
	private final DeletePositionUseCase deletePositionUseCase;

	@PostMapping
	public Mono<ResponseEntity<Position>> create(@RequestBody CreatePositionRequest body) {
		return createPositionUseCase.execute(body)
				.map(createdPosition -> ResponseEntity.status(HttpStatus.CREATED).body(createdPosition))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Position>> update(@PathVariable Long id,
			@RequestBody UpdatePositionRequest body) {
		return updatePositionUseCase.execute(body)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<Position>>> getAll() {
		Flux<Position> positions = getAllPositionUseCase.execute();
		return Mono.just(ResponseEntity.ok(positions));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Position>> getById(@PathVariable Long id) {
		return getByIdPositionUseCase.execute(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
		return deletePositionUseCase.execute(id)
				.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
