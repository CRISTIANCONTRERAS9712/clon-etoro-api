package com.clon.etoro.application.usecase;

public record CreateAssetRequest(String ticker, String name, String description, String logo, String price,
        Boolean active) {
}
