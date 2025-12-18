package com.clon.etoro.application.request;

import java.math.BigDecimal;

/**
 * Request para crear una cuenta
 */
public record CreateAccountRequest(Long userId, BigDecimal cashAvailable) {
}
