package com.clon.etoro.application.request;

import java.time.LocalDateTime;

public record CreatePositionRequest(Long userId, Long assetId, Double units, Double buyPrice, LocalDateTime buyDate) {
}