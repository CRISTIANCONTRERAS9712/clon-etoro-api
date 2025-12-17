package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.infraestructure.entity.CountryEntity;
import com.clon.etoro.infraestructure.repository.SpringDataCountryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresCountryRepositoryAdapter implements CountryRepositoryPort {

	private final SpringDataCountryRepository reactiveRepo;

	@Override
	public Mono<Boolean> isCountryActive(String isoCode) {
		return reactiveRepo.findByIsoCountry(isoCode)
				.map(entity -> {
					return entity.getActive();
				});
	}

	@Override
	public Mono<Country> getCountryByCodeIso(String isoCode) {
		return reactiveRepo.findByIsoCountry(isoCode)
				.map(this::toDomain);
	}

	@Override
	public Mono<Country> getCountryById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Country> save(Country country) {
		return reactiveRepo.save(toEntity(country))
				.map(this::toDomain);
	}

	@Override
	public Mono<Country> update(Country country) {
		return reactiveRepo.save(toEntity(country))
				.map(this::toDomain);
	}

	@Override
	public Flux<Country> findAll() {
		return reactiveRepo.findAll()
				.map(this::toDomain);
	}

	@Override
	public Mono<Country> findById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return reactiveRepo.deleteById(id);
	}

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private CountryEntity toEntity(Country c) {
		CountryEntity e = new CountryEntity();

		e.setIdCountry(c.getId());
		e.setIsoCountry(c.getIso());
		e.setName(c.getName());
		e.setActive(c.getActive());

		return e;
	}

	private Country toDomain(CountryEntity e) {
		Country c = new Country();

		c.setId(e.getIdCountry());
		c.setIso(e.getIsoCountry());
		c.setName(e.getName());
		c.setActive(e.getActive());

		return c;
	}
}
