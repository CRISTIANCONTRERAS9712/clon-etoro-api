package com.clon.etoro.infraestructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.clon.etoro.infraestructure.entity.CountryEntity;
import com.clon.etoro.infraestructure.entity.UserEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataCountryRepository extends ReactiveCrudRepository<CountryEntity, Long> {

	Mono<CountryEntity> findByIsoCountry(String iso);
}
