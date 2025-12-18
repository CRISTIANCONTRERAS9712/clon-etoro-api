package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetByIdPositionUseCase {

	private final PositionRepositoryPort positionRepositoryPort;
	private final UserRepositoryPort userRepository;
	private final AssetRepositoryPort assetRepository;

	public Mono<Position> execute(Long id) {
		return positionRepositoryPort.findById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Position no encontrada")))
				.flatMap(position -> {
					return userRepository.findById(position.getUser().getId())
							.map(user -> {
								position.setUser(user);
								return position;
							});
				})
				.flatMap(position -> {
					return assetRepository.findById(position.getAsset().getId())
							.map(asset -> {
								position.setAsset(asset);
								return position;
							});
				});
	}
}
