package com.clon.etoro.application.usecase;

public record CreateCountryRequest(String isoCountry, String name, Boolean active) {
}