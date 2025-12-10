package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.infraestructure.entity.UserEntity;
import com.clon.etoro.infraestructure.repository.SpringDataUserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresUserRepositoryAdapter implements UserRepositoryPort {

	private final SpringDataUserRepository reactiveRepo;

	@Override
	public Mono<Boolean> existsByEmail(String email) {
		return reactiveRepo.existsByEmail(email)
				.doOnNext(bool -> System.out.println("DEBUG MANUAL: Resultado de reactiveRepo.existsByEmail(email)o: " + bool));
	}

	@Override
	public Mono<User> save(User u) {
		UserEntity entity = toEntity(u);
		return reactiveRepo.save(entity).map(this::toDomain);
	}

	@Override
	public Mono<User> update(User u) {
		return reactiveRepo.findById(u.getIdUser()) // Buscar primero
				.switchIfEmpty(Mono.error(new RuntimeException("User not found")))
				.flatMap(existing -> {
					// Actualizar valores
					existing.setFirstname(u.getFirstname());
					existing.setLastname(u.getLastname());
					existing.setEmail(u.getEmail());
					existing.setBirthdate(u.getBirthdate());
					existing.setPassword(u.getPassword());
					existing.setCellphone(u.getCellphone());
					existing.setCountryId(u.getCountry().getIdCountry());
					
					return reactiveRepo.save(existing);
				}).map(this::toDomain);
	}

	@Override
	public Mono<User> getByEmail(String email) {
		return reactiveRepo.findByEmail(email).map(this::toDomain);
	}

	@Override
	public Flux<User> findAll() {
		return reactiveRepo.findAll().map(this::toDomain);
	}

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private UserEntity toEntity(User u) {
		UserEntity e = new UserEntity();

		e.setIdUser(u.getIdUser()); // si viene null, hace insert; si no, update
		e.setFirstname(u.getFirstname());
		e.setLastname(u.getLastname());
		e.setEmail(u.getEmail());
		e.setBirthdate(u.getBirthdate());
		e.setPassword(u.getPassword());
		e.setCellphone(u.getCellphone());

		if (u.getCountry() != null) {
			e.setCountryId(u.getCountry().getIdCountry());
		}

		return e;
	}

	private User toDomain(UserEntity e) {
		User u = new User();

		u.setIdUser(e.getIdUser());
		u.setFirstname(e.getFirstname());
		u.setLastname(e.getLastname());
		u.setEmail(e.getEmail());
		u.setBirthdate(e.getBirthdate());
		u.setPassword(e.getPassword());
		u.setCellphone(e.getCellphone());

		if (e.getCountryId() != null) {
			Country c = new Country();
			c.setIdCountry(e.getCountryId());
			u.setCountry(c);
		}

		return u;
	}
}
