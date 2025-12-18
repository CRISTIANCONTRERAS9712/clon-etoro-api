package com.clon.etoro.application.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request para crear una cuenta
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    private Long userId;
    private BigDecimal cashAvailable;
}
