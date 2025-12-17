package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.infraestructure.entity.AssetEntity;
import com.clon.etoro.infraestructure.repository.SpringDataAssetRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresAssetRepositoryAdapter implements AssetRepositoryPort {

	private final SpringDataAssetRepository reactiveRepo;

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private AssetEntity toEntity(Asset c) {
		AssetEntity e = new AssetEntity();

		e.setId(c.getId());
		e.setTicker(c.getTicker());
		e.setName(c.getName());
		e.setDescription(c.getDescription());
		e.setLogo(c.getLogo());
		e.setPrice(c.getPrice());
		e.setActive(c.getActive());

		return e;
	}

	private Asset toDomain(AssetEntity e) {
		Asset c = new Asset();

		c.setId(e.getId());
		c.setTicker(e.getTicker());
		c.setName(e.getName());
		c.setDescription(e.getDescription());
		c.setLogo(e.getLogo());
		c.setPrice(e.getPrice());
		c.setActive(e.getActive());

		return c;
	}

	@Override
	public Mono<Asset> save(Asset u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Mono<Asset> update(Asset u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Flux<Asset> findAll() {
		return reactiveRepo.findAll()
				.map(this::toDomain);
	}

	@Override
	public Mono<Asset> findById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return reactiveRepo.deleteById(id);
	}

	@Override
	public Mono<Asset> findByTicker(String ticker) {
		return reactiveRepo.findByTicker(ticker)
				.map(this::toDomain);
	}
}
