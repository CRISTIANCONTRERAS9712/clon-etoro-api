package com.clon.etoro.infraestructure.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.clon.etoro.application.usecase.CreateUserRequest;
import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.application.usecase.GetByIdUserUseCase;
import com.clon.etoro.application.usecase.UpdateUserRequest;
import com.clon.etoro.application.usecase.UpdateUserUseCase;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(UserController.class)
public class UserControllerUnitTestMockito {

    @Autowired
    private WebTestClient webClient;

    // Mock dependencies required by UserController
    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private GetAllUserUseCase getAllUsersUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private GetByIdUserUseCase getByIdUserUseCase;
    
    @Test
    public void testCreateUser_Mocked() {
        User mockUser = new User(
                null,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                LocalDate.of(1997, 12, 21),
                "12345678",
                "3111234578",
                new Country(1L, "CO", "Colombia", true)
                );
        
        CreateUserRequest request = new CreateUserRequest(
        		"Jane",
        		"Doe",
        		"jane.doe@example.com",
        		LocalDate.of(1997, 12, 21),
        		"12345678", 
        		"3111234578", 
        		"CO"
        		);

        // Stub the UseCase, not the repository
        Mockito.when(createUserUseCase.execute(request)).thenReturn(Mono.just(mockUser));

        webClient.post().uri("/users/create")
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(Mono.just(request), CreateUserRequest.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class).isEqualTo(mockUser);
    }
    
    @Test
    public void testUpdateUser_Mocked() {
        User mockUser = new User(
                null,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                LocalDate.of(1997, 12, 21),
                "12345678",
                "3111234578",
                new Country(1L, "CO", "Colombia", true)
                );

        UpdateUserRequest request = new UpdateUserRequest(
        		"Jane",
        		"Doe",
        		"jane.doe@example.com",
        		LocalDate.of(1997, 12, 21),
        		"12345678", 
        		"3111234578", 
        		"CO"
        		);
        
        // Stub the UseCase, not the repository
        Mockito.when(updateUserUseCase.execute(request)).thenReturn(Mono.just(mockUser));

        webClient.put().uri("/users/update")
		        .contentType(MediaType.APPLICATION_JSON)
		        .body(Mono.just(request), CreateUserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(mockUser);
    }

    @Test
    public void testFindUserById_Mocked() {
        User mockUser = new User(
                1L,
                "Jane",
                "Doe",
                "jane.doe@example.com",
                LocalDate.of(1997, 12, 21),
                "12345678",
                "3111234578",
                new Country(1L, "CO", "Colombia", true)
                );

        // Stub the UseCase, not the repository
        Mockito.when(getByIdUserUseCase.execute(1L)).thenReturn(Mono.just(mockUser));

        webClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(mockUser);
    }
    
    @Test
    public void testFindUserByIdNotFound_Mocked() {
        // Stub the UseCase, not the repository
        Mockito.when(getByIdUserUseCase.execute(1L)).thenReturn(Mono.error(new RuntimeException("Usuario no encontrado")));

        webClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }
    
    @Test
    public void testGetAllUsers_Mocked() {
    	 User mockUser = new User(
                 1L,
                 "Jane",
                 "Doe",
                 "jane.doe@example.com",
                 LocalDate.of(1997, 12, 21),
                 "12345678",
                 "3111234578",
                 new Country(1L, "CO", "Colombia", true)
                 );

         User mockUser2 = new User(
                 2L,
                 "John",
                 "Smith",
                 "john.smith@example.com",
                 LocalDate.of(1995, 1, 15),
                 "87654321",
                 "3111234579",
                 new Country(1L, "CO", "Colombia", true)
                 );
         
        // Preparamos la lista esperada
	    List<User> expectedUserList = Arrays.asList(mockUser, mockUser2);

        // Stub the UseCase, not the repository
        Mockito.when(getAllUsersUseCase.execute()).thenReturn(Flux.fromIterable(expectedUserList));

        webClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).isEqualTo(expectedUserList);
    }
}
