package com.clon.etoro.application.usecase;

import lombok.RequiredArgsConstructor;

import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.port.PositionRepositoryPort;

import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllPositionUseCase {

    private final PositionRepositoryPort positionRepository;

    public Flux<Position> execute() {
        return positionRepository.findAll();
    }
}
