package com.clon.etoro.application.request;

public record UpdateAssetRequest(Long id, String ticker, String name, String description, String logo, String price,
        Boolean active) {
}
