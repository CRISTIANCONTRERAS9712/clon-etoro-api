package com.clon.etoro.application.request;

import java.time.LocalDateTime;

public record UpdatePositionRequest(Long id, Long userId, Long assetId, Double units, Double buyPrice,
                LocalDateTime buyDate) {
}