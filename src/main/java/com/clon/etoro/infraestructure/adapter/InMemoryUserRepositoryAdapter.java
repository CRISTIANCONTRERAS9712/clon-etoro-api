package com.clon.etoro.infraestructure.adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepositoryPort;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class InMemoryUserRepositoryAdapter implements UserRepositoryPort {

	// private final Map<Long, User> db = new HashMap<>();
	private final List<User> users = new ArrayList<>();

	public InMemoryUserRepositoryAdapter() {
//        db.put(1L, new User(1L, "Cristian", "Contreras", "1@gmail.com", LocalDate.now(), "12345", "3549878787"));
//        db.put(2L, new User(2L, "Pedro", "Perez", "2@gmail.com", LocalDate.now(), "12345", "3549878787"));
//        db.put(3L, new User(3L, "Juan", "Pinto", "3@gmail.com", LocalDate.now(), "12345", "3549878787"));

		Country country = new Country(1L, "CO", "Colombia", Boolean.TRUE);
		users.add(
				new User(1L, "Cristian", "Contreras", "1@gmail.com", LocalDate.now(), "12345", "3549878787", country));
		users.add(new User(2L, "Pedro", "Perez", "2@gmail.com", LocalDate.now(), "12345", "3549878787", country));
		users.add(new User(3L, "Juan", "Pinto", "3@gmail.com", LocalDate.now(), "12345", "3549878787", country));
	}

	@Override
	public Mono<Boolean> existsByEmail(String email) {
		return Mono.just(users.stream().anyMatch(user -> user.getEmail().equals(email)));
//		boolean exist = users.stream()
//				.anyMatch(user -> user.getEmail().equals(email));
//		return exist;
	}

	@Override
	public Mono<User> save(User user) {
		Long nextId = users.getLast().getIdUser() + 1L;
		user.setIdUser(nextId);
		users.add(user);
		return Mono.just(user).doOnNext(u -> users.add(u));
//		Long nextId = users.getLast().getIdUser() + 1L;
//		u.setIdUser(nextId);
//		users.add(u);
//		return u;
	}

	@Override
	public Mono<User> update(User user) {
		users.removeIf(u -> u.getIdUser().equals(user.getIdUser()));
		users.add(user);
		return Mono.just(user);
//		for (int i = 0; i < users.size(); i++) {
//			if(users.get(i).getIdUser() == u.getIdUser()) {
//				users.set(i, u);
//				return u;
//			}
//		}
//		return null;
	}

	@Override
	public Mono<User> getByEmail(String email) {
		Optional<User> foundUser = users.stream()
				.filter(user -> user.getEmail().equals(email))
				.findFirst();
		return Mono.justOrEmpty(foundUser);
//		Optional<User> foundUser = users.stream()
//				.filter(user -> user.getEmail().equals(email))
//				.findAny();
//		return foundUser;
	}

	@Override
	public Flux<User> findAll() {
		return Flux.fromIterable(users);
//		return users;
	}

    @Override
    public Mono<User> findById(Long id) {
        return Mono.justOrEmpty(
                users.stream()
                        .filter(u -> u.getIdUser().equals(id))
                        .findFirst()
        );
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        users.remove(id);
        return Mono.empty(); // equivalente a reactiveRepo.deleteById()
    }


}
