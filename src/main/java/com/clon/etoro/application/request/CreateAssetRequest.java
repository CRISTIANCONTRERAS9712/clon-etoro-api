package com.clon.etoro.application.request;

public record CreateAssetRequest(String ticker, String name, String description, String logo, String price,
                Boolean active) {
}
