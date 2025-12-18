package com.clon.etoro.application.usecase;

import java.util.Optional;

import com.clon.etoro.application.request.UpdatePositionRequest;
import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdatePositionUseCase {

        private final PositionRepositoryPort positionRepository;
        private final UserRepositoryPort userRepository;
        private final AssetRepositoryPort assetRepository;

        public Mono<Position> execute(UpdatePositionRequest request) {
                return Mono.zip(
                                userRepository.findById(request.userId())
                                                .switchIfEmpty(Mono
                                                                .error(new RuntimeException("Usuario no encontrado"))),
                                assetRepository.findById(request.assetId())
                                                .switchIfEmpty(Mono.error(new RuntimeException("Asset no encontrado"))),
                                positionRepository.findById(request.id())
                                                .switchIfEmpty(Mono
                                                                .error(new RuntimeException("Position no encontrada"))))
                                .map(tuple -> {
                                        User user = tuple.getT1();
                                        Asset asset = tuple.getT2();
                                        Position position = tuple.getT3();

                                        position.setUser(user);
                                        position.setAsset(asset);

                                        Optional.ofNullable(request.units()).ifPresent(position::setUnits);
                                        Optional.ofNullable(request.buyPrice()).ifPresent(position::setBuyPrice);
                                        Optional.ofNullable(request.buyDate()).ifPresent(position::setBuyDate);

                                        return position;
                                })
                                .flatMap(positionRepository::update);
        }

}
