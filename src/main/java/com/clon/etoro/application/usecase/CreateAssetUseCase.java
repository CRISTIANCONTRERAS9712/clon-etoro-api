package com.clon.etoro.application.usecase;

import lombok.RequiredArgsConstructor;

import com.clon.etoro.application.request.CreateAssetRequest;
import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.port.AssetRepositoryPort;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateAssetUseCase {

    private final AssetRepositoryPort assetRepository;

    public Mono<Asset> execute(CreateAssetRequest request) {
        Asset asset = new Asset(
                null,
                request.ticker(),
                request.name(),
                request.description(),
                request.logo(),
                request.price(),
                request.active());
        return assetRepository.save(asset);
    }
}
