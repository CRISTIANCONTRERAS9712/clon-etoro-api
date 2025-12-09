package com.clon.etoro.domain.port;

import java.util.List;
import java.util.Optional;

import com.clon.etoro.domain.model.User;

public interface UserRepository {
	
	boolean existsByEmail(String email);
	User save(User u);
	User update(User u);
	Optional<User> getByEmail(String email);
	List<User> findAll();
}
