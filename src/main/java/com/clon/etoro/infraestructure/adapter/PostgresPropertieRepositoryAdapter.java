package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Propertie;
import com.clon.etoro.domain.port.PropertieRepositoryPort;
import com.clon.etoro.infraestructure.entity.PropertieEntity;
import com.clon.etoro.infraestructure.repository.SpringDataPropertieRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresPropertieRepositoryAdapter implements PropertieRepositoryPort {

	private final SpringDataPropertieRepository reactiveRepo;

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private PropertieEntity toEntity(Propertie c) {
		PropertieEntity e = new PropertieEntity();

		e.setId(c.getId());
		e.setKey(c.getKey());
		e.setValue(c.getValue());
		e.setDescription(c.getDescription());

		return e;
	}

	private Propertie toDomain(PropertieEntity e) {
		Propertie c = new Propertie();

		c.setId(e.getId());
		c.setKey(e.getKey());
		c.setValue(e.getValue());
		c.setDescription(e.getDescription());

		return c;
	}

	@Override
	public Mono<Propertie> save(Propertie u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Mono<Propertie> update(Propertie u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Flux<Propertie> findAll() {
		return reactiveRepo.findAll()
				.map(this::toDomain);
	}

	@Override
	public Mono<Propertie> findById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return reactiveRepo.deleteById(id);
	}

	@Override
	public Mono<Propertie> findByKey(String key) {
		return reactiveRepo.findByKey(key)
				.map(this::toDomain);
	}
}
