package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.port.PositionRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetByIdPositionUseCase {

	private final PositionRepositoryPort positionRepositoryPort;

	public Mono<Position> execute(Long id) {
		return positionRepositoryPort.findById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Position no encontrada")));
	}
}
