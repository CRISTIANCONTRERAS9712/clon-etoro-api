package com.clon.etoro.application.usecase;

public record UpdateAssetRequest(Long id, String ticker, String name, String description, String logo, String price,
                Boolean active) {
}
