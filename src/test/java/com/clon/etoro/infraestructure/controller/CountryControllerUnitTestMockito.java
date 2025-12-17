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

import com.clon.etoro.application.usecase.CreateCountryRequest;
import com.clon.etoro.application.usecase.CreateCountryUseCase;
import com.clon.etoro.application.usecase.CreateUserRequest;
import com.clon.etoro.application.usecase.CreateUserUseCase;
import com.clon.etoro.application.usecase.DeleteCountryUseCase;
import com.clon.etoro.application.usecase.DeleteUserUseCase;
import com.clon.etoro.application.usecase.GetAllCountryUseCase;
import com.clon.etoro.application.usecase.GetAllUserUseCase;
import com.clon.etoro.application.usecase.GetByIdCountryUseCase;
import com.clon.etoro.application.usecase.GetByIdUserUseCase;
import com.clon.etoro.application.usecase.UpdateCountryRequest;
import com.clon.etoro.application.usecase.UpdateCountryUseCase;
import com.clon.etoro.application.usecase.UpdateUserRequest;
import com.clon.etoro.application.usecase.UpdateUserUseCase;
import com.clon.etoro.domain.model.Country;
import com.clon.etoro.domain.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(CountryController.class)
public class CountryControllerUnitTestMockito {

        @Autowired
        private WebTestClient webClient;

        @MockitoBean
        private CreateCountryUseCase createCountryUseCase;

        @MockitoBean
        private GetAllCountryUseCase getAllCountriesUseCase;

        @MockitoBean
        private UpdateCountryUseCase updateCountryUseCase;

        @MockitoBean
        private GetByIdCountryUseCase getByIdCountryUseCase;

        @MockitoBean
        private DeleteCountryUseCase deleteCountryUseCase;

        // Métodos auxiliares para crear datos de prueba (DRY principle)
        private Country defaultCountry;

        @BeforeEach // Se ejecuta antes de cada @Test
        public void setUp() {
                defaultCountry = new Country(1L, "CO", "Colombia", true);
        }

        // ------------------ TESTS ------------------

        @Test
        public void testCreateCountry_ShouldReturnCreatedCountry() {
                // Objeto específico para la request (sin ID)
                CreateCountryRequest request = new CreateCountryRequest(
                                "CO",
                                "Colombia",
                                true);

                // Usamos any() para ser menos acoplados a la instancia exacta de 'request'
                Mockito.when(createCountryUseCase.execute(Mockito.any(CreateCountryRequest.class)))
                                .thenReturn(Mono.just(defaultCountry));

                webClient.post().uri("/country")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(request) // Uso de bodyValue() (más conciso)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Country.class).isEqualTo(defaultCountry);
        }

        @Test
        public void testUpdateCountry_ShouldReturnOkStatusAndUpdatedCountry() {
                UpdateCountryRequest request = new UpdateCountryRequest(
                                1L,
                                "CO",
                                "Colombia",
                                true);

                // Creamos una versión "actualizada" del usuario para la respuesta mockeada
                Country updatedCountry = new Country(
                                1L,
                                "CO",
                                "Colombia",
                                true);

                Mockito.when(updateCountryUseCase.execute(Mockito.any(UpdateCountryRequest.class)))
                                .thenReturn(Mono.just(updatedCountry));

                // Nota: Si tu endpoint PUT requiere un ID en la URL (ej. /users/update/1),
                // debes cambiar la URI
                webClient.put().uri("/country/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(request) // Uso de bodyValue()
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(Country.class).isEqualTo(updatedCountry);
        }

        @Test
        public void testFindCountryById_ShouldReturnOkStatusAndCountry() {
                Long countryId = 1L;

                Mockito.when(getByIdCountryUseCase.execute(countryId))
                                .thenReturn(Mono.just(defaultCountry));

                webClient.get().uri("/country/{id}", countryId) // Mejor forma de pasar IDs a la URI
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(Country.class).isEqualTo(defaultCountry);
        }

        @Test
        public void testFindCountryById_ShouldReturnNotFoundWhenCountryDoesNotExist() {
                Long nonExistentCountryId = 99L;
                // Asumiendo que tu controlador maneja la excepción del UseCase y la convierte
                // en un 404
                Mockito.when(getByIdCountryUseCase.execute(nonExistentCountryId))
                                // .thenReturn(Mono.empty()); // Usualmente un Mono.empty() resulta en 404 en
                                // Spring WebFlux
                                .thenReturn(Mono.error(new RuntimeException("Pais no encontrado")));

                webClient.get().uri("/country/{id}", nonExistentCountryId)
                                .exchange()
                                .expectStatus().isNotFound();
        }

        @Test
        public void testGetAllCountries_ShouldReturnListOfCountries() {
                Country mockCountry2 = new Country(
                                2L,
                                "BR",
                                "Brazil",
                                true);

                List<Country> expectedCountryList = Arrays.asList(defaultCountry, mockCountry2);

                Mockito.when(getAllCountriesUseCase.execute())
                                .thenReturn(Flux.fromIterable(expectedCountryList));

                webClient.get().uri("/country")
                                .exchange()
                                .expectStatus().isOk()
                                .expectBodyList(Country.class)
                                .isEqualTo(expectedCountryList);
        }
}
