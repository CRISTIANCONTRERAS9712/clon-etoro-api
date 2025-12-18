package com.clon.etoro.application.usecase;

import com.clon.etoro.domain.port.PositionRepositoryPort;
import reactor.core.publisher.Mono;

public class DeletePositionUseCase {

    private final PositionRepositoryPort positionRepository;

    public DeletePositionUseCase(PositionRepositoryPort positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Mono<Void> execute(Long id) {
        return positionRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Position not found")))
                .then(positionRepository.deleteById(id));
    }
}
