package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.CountryRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;
import com.clon.etoro.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UserRepositoryPort userRepo;
    private final CountryRepositoryPort countryRepo;

    public Mono<User> execute(UpdateUserRequest updateUserRequest) {

        return userRepo.findById(updateUserRequest.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(existingUser ->
                        countryRepo.getCountryByCodeIso(updateUserRequest.getIsoCountry())
                                .switchIfEmpty(Mono.error(new RuntimeException("Country not found")))
                                .flatMap(country -> {

                                    // Actualizar datos excepto el email
                                    // Actualizar datos solo si vienen en el request (no null)
                                    if (updateUserRequest.getFirstname() != null) {
                                        existingUser.setFirstname(updateUserRequest.getFirstname());
                                    }

                                    if (updateUserRequest.getLastname() != null) {
                                        existingUser.setLastname(updateUserRequest.getLastname());
                                    }

                                    if (updateUserRequest.getCellphone() != null) {
                                        existingUser.setCellphone(updateUserRequest.getCellphone());
                                    }

                                    if (updateUserRequest.getPassword() != null) {
                                        existingUser.setPassword(updateUserRequest.getPassword());
                                    }

                                    if (updateUserRequest.getBirthdate() != null) {
                                        existingUser.setBirthdate(updateUserRequest.getBirthdate());
                                    }

                                    // Asignar país
                                    existingUser.setCountry(country);

                                    return userRepo.update(existingUser).map(updatedUser -> {
                                        updatedUser.setCountry(country);
                                        return updatedUser;
                                    });
                                })
                );
    }
}
