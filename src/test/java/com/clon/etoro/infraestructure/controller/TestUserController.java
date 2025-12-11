package com.clon.etoro.infraestructure.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.clon.etoro.application.usecase.CreateUserRequest;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.UserRepositoryPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserController {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepositoryPort userRepository;

    @Test
    public void testGetAllUsers() {
        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .consumeWith(response -> {
                    List<User> users = response.getResponseBody();
                    assertThat(users).isNotEmpty();
                    assertThat(users.get(0).getIdUser()).isNotNull();
                });
    }

    // @Test
    // void get() {
    // User user = createSampleUser();

    // webTestClient.get()
    // .uri("/users/{id}", user.getIdUser())
    // .exchange()
    // .expectStatus().isOk()
    // .expectBody()
    // .jsonPath("$.firstname").isEqualTo(user.getFirstname())
    // .jsonPath("$.lastname").isEqualTo(user.getLastname())
    // .jsonPath("$.id").isEqualTo(user.getIdUser());
    // }

    // @Test
    // void getNotFound() {
    // webTestClient.get()
    // .uri("/users/{id}", "id que no existe")
    // .exchange()
    // .expectStatus().isNotFound()
    // .expectBody().isEmpty();
    // }

    @Test
    void create() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "Mateo",
                "Corredor",
                "m255@gmail.com",
                LocalDate.of(1997, 12, 12),
                "12345678",
                "3111234578",
                "CO");

        webTestClient.post()
                .uri("/users/create")
                .bodyValue(createUserRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.idUser").isNotEmpty()
                .jsonPath("$.firstname").isEqualTo(createUserRequest.firstname())
                .jsonPath("$.lastname").isEqualTo(createUserRequest.lastname());
    }

    // @Test
    // void delete() {
    // User user = createSampleUser();

    // webTestClient.delete()
    // .uri("/users/{id}", user.getIdUser())
    // .exchange()
    // .expectStatus().isNoContent()
    // .expectBody().isEmpty();
    // }

    // @Test
    // void deleteNotFound() {
    // webTestClient.delete()
    // .uri("/users/{id}", "id que no existe")
    // .exchange()
    // .expectStatus().isNotFound()
    // .expectBody().isEmpty();
    // }

    private User createSampleUser() {
        User user = new User();
        user.setEmail("m2@gmail.com");
        user.setFirstname("Mateo");
        user.setLastname("Corredor");
        user.setBirthdate(LocalDate.now());
        user.setCellphone("3111234578");
        user.setPassword("12345678");
        user.setCountry(new Country(1L));

        return userRepository.save(user).block();
    }
}
