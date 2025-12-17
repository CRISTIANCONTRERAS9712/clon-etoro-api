package com.clon.etoro.application.usecase;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.port.AssetRepositoryPort;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateAssetUseCase {

    private final AssetRepositoryPort assetRepository;

    public Mono<Asset> execute(UpdateAssetRequest request) {

        // 1️⃣ Buscar asset
        Mono<Asset> assetMono = assetRepository.findById(request.id())
                .switchIfEmpty(Mono.error(new RuntimeException("Asset not found")));

        // 2️⃣ Actualizar asset
        return assetMono
                .flatMap(asset -> {
                    Optional.ofNullable(request.ticker()).ifPresent(asset::setTicker);
                    Optional.ofNullable(request.name()).ifPresent(asset::setName);
                    Optional.ofNullable(request.description()).ifPresent(asset::setDescription);
                    Optional.ofNullable(request.logo()).ifPresent(asset::setLogo);
                    Optional.ofNullable(request.price()).ifPresent(asset::setPrice);
                    Optional.ofNullable(request.active()).ifPresent(asset::setActive);
                    return assetRepository.update(asset);
                });
    }
}
