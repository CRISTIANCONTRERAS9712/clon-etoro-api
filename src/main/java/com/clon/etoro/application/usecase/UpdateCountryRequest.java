package com.clon.etoro.application.usecase;

public record UpdateCountryRequest(Long id, String iso, String name, Boolean active) {
}