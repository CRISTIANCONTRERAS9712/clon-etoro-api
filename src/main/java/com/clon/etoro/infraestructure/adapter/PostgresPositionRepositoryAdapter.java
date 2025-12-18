package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.infraestructure.entity.PositionEntity;
import com.clon.etoro.infraestructure.repository.SpringDataPositionRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresPositionRepositoryAdapter implements PositionRepositoryPort {

	private final SpringDataPositionRepository reactiveRepo;

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private PositionEntity toEntity(Position c) {
		PositionEntity e = new PositionEntity();

		e.setId(c.getId());
		e.setUnits(c.getUnits());
		e.setBuyPrice(c.getBuyPrice());
		e.setBuyDate(c.getBuyDate());
		e.setUserId(c.getUser().getId());
		e.setAssetId(c.getAsset().getId());

		return e;
	}

	private Position toDomain(PositionEntity e) {
		Position c = new Position();

		c.setId(e.getId());
		c.setUnits(e.getUnits());
		c.setBuyPrice(e.getBuyPrice());
		c.setBuyDate(e.getBuyDate());
		c.setUser(new User(e.getUserId()));
		c.setAsset(new Asset(e.getAssetId()));

		return c;
	}

	@Override
	public Mono<Position> save(Position u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Mono<Position> update(Position u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Flux<Position> findAll() {
		return reactiveRepo.findAll()
				.map(this::toDomain);
	}

	@Override
	public Mono<Position> findById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return reactiveRepo.deleteById(id);
	}

	@Override
	public Mono<Position> findByUserId(Long userId) {
		return reactiveRepo.findByUserId(userId)
				.map(this::toDomain);
	}

	@Override
	public Mono<Position> findByAssetId(Long assetId) {
		return reactiveRepo.findByAssetId(assetId)
				.map(this::toDomain);
	}

	@Override
	public Mono<Position> findByUserIdAndAssetId(Long userId, Long assetId) {
		return reactiveRepo.findByUserIdAndAssetId(userId, assetId)
				.map(this::toDomain);
	}
}
