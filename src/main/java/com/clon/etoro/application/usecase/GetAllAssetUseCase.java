package com.clon.etoro.application.usecase;

import lombok.RequiredArgsConstructor;

import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.port.AssetRepositoryPort;

import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllAssetUseCase {

    private final AssetRepositoryPort assetRepository;

    public Flux<Asset> execute() {
        return assetRepository.findAll();
    }
}
