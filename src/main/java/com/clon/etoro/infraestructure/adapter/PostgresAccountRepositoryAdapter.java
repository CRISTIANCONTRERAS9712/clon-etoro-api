package com.clon.etoro.infraestructure.adapter;

import com.clon.etoro.domain.model.Account;
import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.AccountRepositoryPort;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.infraestructure.entity.AccountEntity;
import com.clon.etoro.infraestructure.entity.AssetEntity;
import com.clon.etoro.infraestructure.repository.SpringDataAccountRepository;
import com.clon.etoro.infraestructure.repository.SpringDataAssetRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PostgresAccountRepositoryAdapter implements AccountRepositoryPort {

	private final SpringDataAccountRepository reactiveRepo;

	// -------------------------------------------------------
	// MAPPERS (Entity <-> Domain)
	// -------------------------------------------------------

	private AccountEntity toEntity(Account c) {
		AccountEntity e = new AccountEntity();

		e.setId(c.getId());
		e.setCashAvailable(c.getCashAvailable());
		e.setUserId(c.getUser().getId());

		return e;
	}

	private Account toDomain(AccountEntity e) {
		Account c = new Account();

		c.setId(e.getId());
		c.setCashAvailable(e.getCashAvailable());
		c.setUser(new User(e.getUserId()));

		return c;
	}

	@Override
	public Mono<Account> save(Account u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Mono<Account> update(Account u) {
		return reactiveRepo.save(toEntity(u))
				.map(this::toDomain);
	}

	@Override
	public Flux<Account> findAll() {
		return reactiveRepo.findAll()
				.map(this::toDomain);
	}

	@Override
	public Mono<Account> findById(Long id) {
		return reactiveRepo.findById(id)
				.map(this::toDomain);
	}

	@Override
	public Mono<Void> deleteById(Long id) {
		return reactiveRepo.deleteById(id);
	}

	@Override
	public Mono<Account> findByUserId(Long userId) {
		return reactiveRepo.findByUserId(userId)
				.map(this::toDomain);
	}
}
