package com.clon.etoro.application.usecase;

import lombok.RequiredArgsConstructor;

import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllPositionUseCase {

    private final PositionRepositoryPort positionRepository;
    private final UserRepositoryPort userRepository;
    private final AssetRepositoryPort assetRepository;

    public Flux<Position> execute() {
        return positionRepository.findAll()
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
