package com.clon.etoro.infraestructure.controller;

import com.clon.etoro.application.request.CreateAssetRequest;
import com.clon.etoro.application.request.UpdateAssetRequest;
import com.clon.etoro.application.usecase.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clon.etoro.domain.model.Asset;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {

	private final CreateAssetUseCase createAssetUseCase;
	private final UpdateAssetUseCase updateAssetUseCase;
	private final GetAllAssetUseCase getAllAssetUseCase;
	private final GetByIdAssetUseCase getByIdAssetUseCase;
	private final DeleteAssetUseCase deleteAssetUseCase;

	@PostMapping
	public Mono<ResponseEntity<Asset>> create(@RequestBody CreateAssetRequest body) {
		return createAssetUseCase.execute(body)
				.map(createdAsset -> ResponseEntity.status(HttpStatus.CREATED).body(createdAsset))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Asset>> update(@PathVariable Long id,
			@RequestBody UpdateAssetRequest body) {
		return updateAssetUseCase.execute(body)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<Asset>>> getAll() {
		Flux<Asset> assets = getAllAssetUseCase.execute();
		return Mono.just(ResponseEntity.ok(assets));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Asset>> getById(@PathVariable Long id) {
		return getByIdAssetUseCase.execute(id)
				.map(ResponseEntity::ok)
				.onErrorResume(
						RuntimeException.class,
						e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
		return deleteAssetUseCase.execute(id)
				.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
