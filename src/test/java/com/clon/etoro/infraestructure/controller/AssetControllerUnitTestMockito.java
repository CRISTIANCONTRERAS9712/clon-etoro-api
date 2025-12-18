package com.clon.etoro.infraestructure.controller;

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

import com.clon.etoro.application.request.CreateAssetRequest;
import com.clon.etoro.application.request.UpdateAssetRequest;
import com.clon.etoro.application.usecase.CreateAssetUseCase;
import com.clon.etoro.application.usecase.DeleteAssetUseCase;
import com.clon.etoro.application.usecase.GetAllAssetUseCase;
import com.clon.etoro.application.usecase.GetByIdAssetUseCase;
import com.clon.etoro.application.usecase.UpdateAssetUseCase;
import com.clon.etoro.domain.model.Asset;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(AssetController.class)
public class AssetControllerUnitTestMockito {

        @Autowired
        private WebTestClient webClient;

        @MockitoBean
        private CreateAssetUseCase createAssetUseCase;

        @MockitoBean
        private GetAllAssetUseCase getAllAssetsUseCase;

        @MockitoBean
        private UpdateAssetUseCase updateAssetUseCase;

        @MockitoBean
        private GetByIdAssetUseCase getByIdAssetUseCase;

        @MockitoBean
        private DeleteAssetUseCase deleteAssetUseCase;

        // Métodos auxiliares para crear datos de prueba (DRY principle)
        private Asset defaultAsset;

        @BeforeEach // Se ejecuta antes de cada @Test
        public void setUp() {
                defaultAsset = new Asset(
                                1L,
                                "GOOG",
                                "Google",
                                "Empresa Google",
                                "https://example.com/goog.png",
                                "200",
                                true);
        }

        // ------------------ TESTS ------------------

        @Test
        public void testCreateAsset_ShouldReturnCreatedAsset() {
                // Objeto específico para la request (sin ID)
                CreateAssetRequest request = new CreateAssetRequest(
                                "GOOG",
                                "Google",
                                "GOOG",
                                "https://example.com/goog.png",
                                "200",
                                true);

                // Usamos any() para ser menos acoplados a la instancia exacta de 'request'
                Mockito.when(createAssetUseCase.execute(Mockito.any(CreateAssetRequest.class)))
                                .thenReturn(Mono.just(defaultAsset));

                webClient.post().uri("/asset")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(request) // Uso de bodyValue() (más conciso)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(Asset.class).isEqualTo(defaultAsset);
        }

        @Test
        public void testUpdateAsset_ShouldReturnOkStatusAndUpdatedAsset() {
                UpdateAssetRequest request = new UpdateAssetRequest(
                                1L,
                                "GOOG",
                                "Google",
                                "GOOG",
                                "https://example.com/goog.png",
                                "200",
                                true);

                // Creamos una versión "actualizada" del usuario para la respuesta mockeada
                Asset updatedAsset = new Asset(
                                1L,
                                "GOOG",
                                "Google",
                                "GOOG",
                                "https://example.com/goog.png",
                                "200",
                                true);

                Mockito.when(updateAssetUseCase.execute(Mockito.any(UpdateAssetRequest.class)))
                                .thenReturn(Mono.just(updatedAsset));

                // Nota: Si tu endpoint PUT requiere un ID en la URL (ej. /users/update/1),
                // debes cambiar la URI
                webClient.put().uri("/asset/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(request) // Uso de bodyValue()
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(Asset.class).isEqualTo(updatedAsset);
        }

        @Test
        public void testFindAssetById_ShouldReturnOkStatusAndAsset() {
                Long assetId = 1L;

                Mockito.when(getByIdAssetUseCase.execute(assetId))
                                .thenReturn(Mono.just(defaultAsset));

                webClient.get().uri("/asset/{id}", assetId) // Mejor forma de pasar IDs a la URI
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(Asset.class).isEqualTo(defaultAsset);
        }

        @Test
        public void testFindAssetById_ShouldReturnNotFoundWhenAssetDoesNotExist() {
                Long nonExistentAssetId = 99L;
                // Asumiendo que tu controlador maneja la excepción del UseCase y la convierte
                // en un 404
                Mockito.when(getByIdAssetUseCase.execute(nonExistentAssetId))
                                // .thenReturn(Mono.empty()); // Usualmente un Mono.empty() resulta en 404 en
                                // Spring WebFlux
                                .thenReturn(Mono.error(new RuntimeException("Asset no encontrado")));

                webClient.get().uri("/asset/{id}", nonExistentAssetId)
                                .exchange()
                                .expectStatus().isNotFound();
        }

        @Test
        public void testGetAllAssets_ShouldReturnListOfAssets() {
                Asset mockAsset2 = new Asset(
                                2L,
                                "META",
                                "Meta",
                                "Empresa Meta",
                                "https://example.com/meta.png",
                                "700",
                                true);

                List<Asset> expectedAssetList = Arrays.asList(defaultAsset, mockAsset2);

                Mockito.when(getAllAssetsUseCase.execute())
                                .thenReturn(Flux.fromIterable(expectedAssetList));

                webClient.get().uri("/asset")
                                .exchange()
                                .expectStatus().isOk()
                                .expectBodyList(Asset.class)
                                .isEqualTo(expectedAssetList);
        }
}
