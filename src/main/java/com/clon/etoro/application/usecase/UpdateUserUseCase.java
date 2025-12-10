package com.clon.etoro.application.usecase;

import java.util.Optional;

import com.clon.etoro.domain.model.Country;
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

    // public Mono<User> execute(UpdateUserRequest updateUserRequest) {

    // return userRepo.findById(updateUserRequest.getId())
    // .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
    // .flatMap(existingUser ->
    // countryRepo.getCountryByCodeIso(updateUserRequest.getIsoCountry())
    // .switchIfEmpty(Mono.error(new RuntimeException("Country not found")))
    // .flatMap(country -> {

    // // Actualizar datos excepto el email
    // // Actualizar datos solo si vienen en el request (no null)
    // if (updateUserRequest.getFirstname() != null) {
    // existingUser.setFirstname(updateUserRequest.getFirstname());
    // }

    // if (updateUserRequest.getLastname() != null) {
    // existingUser.setLastname(updateUserRequest.getLastname());
    // }

    // if (updateUserRequest.getCellphone() != null) {
    // existingUser.setCellphone(updateUserRequest.getCellphone());
    // }

    // if (updateUserRequest.getPassword() != null) {
    // existingUser.setPassword(updateUserRequest.getPassword());
    // }

    // if (updateUserRequest.getBirthdate() != null) {
    // existingUser.setBirthdate(updateUserRequest.getBirthdate());
    // }

    // // Asignar país
    // existingUser.setCountry(country);

    // return userRepo.update(existingUser).map(updatedUser -> {
    // updatedUser.setCountry(country);
    // return updatedUser;
    // });
    // })
    // );
    // }

    // public Mono<User> execute(UpdateUserRequest updateUserRequest) {

    // // 1. Verificar si el usuario existe primero
    // Mono<User> existingUserMono = userRepo.findById(updateUserRequest.getId())
    // .switchIfEmpty(Mono.error(new RuntimeException("User not found")));

    // // 2. Verificar si el país existe (independientemente del usuario, si es
    // // posible)
    // Mono<Country> countryMono =
    // countryRepo.getCountryByCodeIso(updateUserRequest.getIsoCountry())
    // .switchIfEmpty(Mono.error(new RuntimeException("Country not found")));

    // // 3. Combinar los dos Mono de forma reactiva y atómica
    // return Mono.zip(existingUserMono, countryMono)
    // .flatMap(tuple -> {
    // User existingUser = tuple.getT1();
    // Country country = tuple.getT2();

    // // Usamos Optional.ofNullable para actualizar campos de forma concisa y
    // // funcional
    // Optional.ofNullable(updateUserRequest.getFirstname()).ifPresent(existingUser::setFirstname);
    // Optional.ofNullable(updateUserRequest.getLastname()).ifPresent(existingUser::setLastname);
    // Optional.ofNullable(updateUserRequest.getCellphone()).ifPresent(existingUser::setCellphone);
    // Optional.ofNullable(updateUserRequest.getPassword()).ifPresent(existingUser::setPassword);
    // Optional.ofNullable(updateUserRequest.getBirthdate()).ifPresent(existingUser::setBirthdate);

    // // Asignar el país
    // existingUser.setCountry(country);

    // // 4. Guardar el usuario actualizado
    // // Asumimos que userRepo.update(user) devuelve Mono<User>
    // return userRepo.update(existingUser).map(updatedUser -> {
    // updatedUser.setCountry(country);
    // return updatedUser;
    // });
    // });
    // }

    public Mono<User> execute(UpdateUserRequest updateUserRequest) {

        // --- Lógica existente para preparar Mono de Usuario y Mono de País condicional
        // ---

        // 1. Verificar si el usuario existe primero
        Mono<User> existingUserMono = userRepo.findById(updateUserRequest.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));

        // 2. Lógica Condicional para el País (prepara el Mono<Country> si es necesario)
        Mono<Country> countryMono = Optional.ofNullable(updateUserRequest.getIsoCountry())
                .map(isoCountry -> countryRepo.getCountryByCodeIso(isoCountry)
                        .switchIfEmpty(Mono.error(new RuntimeException("Country not found"))))
                .orElse(Mono.empty());

        // --- Lógica principal del flujo ---

        return existingUserMono
                .flatMap(existingUser -> countryMono
                        .map(country -> {
                            // Si se encontró un país nuevo, se setea antes del guardado
                            existingUser.setCountry(country);
                            return existingUser;
                        })
                        .defaultIfEmpty(existingUser) // Si no se cambia el país, se usa el existingUser original
                )
                .flatMap(userToUpdate -> {
                    // 4. Actualizar datos del usuario si vienen en el request (no null)
                    Optional.ofNullable(updateUserRequest.getFirstname()).ifPresent(userToUpdate::setFirstname);
                    Optional.ofNullable(updateUserRequest.getLastname()).ifPresent(userToUpdate::setLastname);
                    Optional.ofNullable(updateUserRequest.getCellphone()).ifPresent(userToUpdate::setCellphone);
                    Optional.ofNullable(updateUserRequest.getPassword()).ifPresent(userToUpdate::setPassword);
                    Optional.ofNullable(updateUserRequest.getBirthdate()).ifPresent(userToUpdate::setBirthdate);

                    // 5. Guardar el usuario actualizado (devuelve User con Country incompleto/solo
                    // ISO)
                    return userRepo.update(userToUpdate);
                })

                // 6. ¡NUEVO PASO! flatMap para enriquecer el objeto User con el Country
                // completo
                .flatMap(savedUser -> {
                    // Utilizamos el ISO del país que está en el usuario guardado (solo ISO)
                    Long idCountry = savedUser.getCountry().getIdCountry();
                    // Consultamos el país completo de nuevo (Operación Asíncrona)
                    return countryRepo.getCountryById(idCountry)
                            // Cuando tengamos el país completo, lo asignamos al User guardado
                            .map(fullCountry -> {
                                savedUser.setCountry(fullCountry);
                                return savedUser; // Devolvemos el User final y completo
                            })
                            .switchIfEmpty(Mono.just(savedUser)); // Manejar si por alguna razón no se encuentra el país
                                                                  // (aunque ya se validó antes)
                });
    }
}
