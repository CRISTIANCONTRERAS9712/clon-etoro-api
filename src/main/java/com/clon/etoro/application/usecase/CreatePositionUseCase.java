package com.clon.etoro.application.usecase;

import com.clon.etoro.application.request.CreatePositionRequest;
import com.clon.etoro.domain.model.Asset;
import com.clon.etoro.domain.model.Position;
import com.clon.etoro.domain.model.User;
import com.clon.etoro.domain.port.AssetRepositoryPort;
import com.clon.etoro.domain.port.PositionRepositoryPort;
import com.clon.etoro.domain.port.UserRepositoryPort;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreatePositionUseCase {

    private final PositionRepositoryPort positionRepository;
    private final UserRepositoryPort userRepository;
    private final AssetRepositoryPort assetRepository;

    public Mono<Position> execute(CreatePositionRequest request) {
        return Mono.zip(
                userRepository.findById(request.userId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado"))),
                assetRepository.findById(request.assetId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Asset no encontrado"))))
                .map(tuple -> {
                    User user = tuple.getT1();
                    Asset asset = tuple.getT2();

                    Position position = new Position();
                    position.setUser(user);
                    position.setAsset(asset);
                    position.setUnits(request.units());
                    position.setBuyPrice(request.buyPrice());
                    position.setBuyDate(request.buyDate());

                    return position;
                })
                .flatMap(positionRepository::save);
    }

}
