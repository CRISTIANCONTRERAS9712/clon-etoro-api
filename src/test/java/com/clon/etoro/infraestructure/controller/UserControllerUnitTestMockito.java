package com.clon.etoro.infraestructure.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private GetAllUserUseCase getAllUsersUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private GetByIdUserUseCase getByIdUserUseCase;
    
    // Métodos auxiliares para crear datos de prueba (DRY principle)
    private Country defaultCountry;
    private User defaultUser;

    @BeforeEach // Se ejecuta antes de cada @Test
    public void setUp() {
        defaultCountry = new Country(1L, "CO", "Colombia", true);
        defaultUser = new User(
                1L, // ID not null for general use
                "Jane",
                "Doe",
                "jane.doe@example.com",
                LocalDate.of(1997, 12, 21),
                "12345678",
                "3111234578",
                defaultCountry
        );
    }
    
    // ------------------ TESTS ------------------
    
    @Test
    public void testCreateUser_ShouldReturnCreatedUser() {
        // Objeto específico para la request (sin ID)
        CreateUserRequest request = new CreateUserRequest(
        		"Jane", 
        		"Doe", 
        		"jane.doe@example.com", 
        		LocalDate.of(1997, 12, 21),
        		"12345678", 
        		"3111234578", 
        		"CO"
        );

        // Usamos any() para ser menos acoplados a la instancia exacta de 'request'
        Mockito.when(createUserUseCase.execute(Mockito.any(CreateUserRequest.class)))
               .thenReturn(Mono.just(defaultUser));

        webClient.post().uri("/users/create")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(request) // Uso de bodyValue() (más conciso)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class).isEqualTo(defaultUser);
    }
    
    @Test
    public void testUpdateUser_ShouldReturnOkStatusAndUpdatedUser() {
        UpdateUserRequest request = new UpdateUserRequest(
        		"Jane_Updated", 
        		"Doe", 
        		"jane.doe@example.com", 
        		LocalDate.of(1997, 12, 21),
        		"12345678", 
        		"3111234578", 
        		"CO"
        );
        
        // Creamos una versión "actualizada" del usuario para la respuesta mockeada
        User updatedUser = new User(
        		1L, 
        		"Jane_Updated", 
        		defaultUser.getLastname(),
        		defaultUser.getEmail(), 
        		defaultUser.getBirthdate(),
        		defaultUser.getPassword(),
        		defaultUser.getCellphone(),
        		defaultCountry
        		);

        Mockito.when(updateUserUseCase.execute(Mockito.any(UpdateUserRequest.class)))
               .thenReturn(Mono.just(updatedUser));

        // Nota: Si tu endpoint PUT requiere un ID en la URL (ej. /users/update/1), debes cambiar la URI
        webClient.put().uri("/users/update")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(request) // Uso de bodyValue()
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(updatedUser);
    }

    @Test
    public void testFindUserById_ShouldReturnOkStatusAndUser() {
        Long userId = 1L;

        Mockito.when(getByIdUserUseCase.execute(userId))
               .thenReturn(Mono.just(defaultUser));

        webClient.get().uri("/users/{id}", userId) // Mejor forma de pasar IDs a la URI
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class).isEqualTo(defaultUser);
    }
    
    @Test
    public void testFindUserById_ShouldReturnNotFoundWhenUserDoesNotExist() {
        Long nonExistentUserId = 99L;
        // Asumiendo que tu controlador maneja la excepción del UseCase y la convierte en un 404
        Mockito.when(getByIdUserUseCase.execute(nonExistentUserId))
                .thenReturn(Mono.empty()); // Usualmente un Mono.empty() resulta en 404 en Spring WebFlux

        webClient.get().uri("/users/{id}", nonExistentUserId)
                .exchange()
                .expectStatus().isNotFound();
    }
    
    @Test
    public void testGetAllUsers_ShouldReturnListOfUsers() {
    	 User mockUser2 = new User(
                 2L, "John", "Smith", "john.smith@example.com", LocalDate.of(1995, 1, 15),
                 "87654321", "3111234579", defaultCountry
         );
         
	    List<User> expectedUserList = Arrays.asList(defaultUser, mockUser2);

        Mockito.when(getAllUsersUseCase.execute())
               .thenReturn(Flux.fromIterable(expectedUserList));

        webClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .isEqualTo(expectedUserList);
    }
}
