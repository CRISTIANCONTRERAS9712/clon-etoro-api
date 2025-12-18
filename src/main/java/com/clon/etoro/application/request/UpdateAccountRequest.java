package com.clon.etoro.application.request;

import java.math.BigDecimal;

/**
 * Request para actualizar una cuenta
 */
public record UpdateAccountRequest(Long accountId, Long userId, Double cashAvailable) {
}
