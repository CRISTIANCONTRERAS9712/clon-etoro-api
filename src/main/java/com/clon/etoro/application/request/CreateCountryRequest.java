package com.clon.etoro.application.request;

public record CreateCountryRequest(String isoCountry, String name, Boolean active) {
}