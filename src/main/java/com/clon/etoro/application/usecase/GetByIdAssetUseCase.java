package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.port.AssetRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetByIdAssetUseCase {

	private final AssetRepositoryPort assetRepositoryPort;

	public Mono<Asset> execute(Long id) {
		return assetRepositoryPort.findById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Asset no encontrado")));
	}
}
