package com.clon.etoro.application.usecase;

import java.time.LocalDateTime;

public record UpdatePositionRequest(Long id, Long userId, Long assetId, Double units, Double buyPrice,
        LocalDateTime buyDate) {
}