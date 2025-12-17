package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.port.AssetRepositoryPort;
import reactor.core.publisher.Mono;

public class DeleteAssetUseCase {

    private final AssetRepositoryPort assetRepository;

    public DeleteAssetUseCase(AssetRepositoryPort assetRepository) {
        this.assetRepository = assetRepository;
    }

    public Mono<Void> execute(Long id) {
        return assetRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Asset no encontrado")))
                .then(assetRepository.deleteById(id));
    }
}
