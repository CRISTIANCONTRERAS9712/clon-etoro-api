package com.clon.etoro.application.request;

public record UpdateCountryRequest(Long id, String iso, String name, Boolean active) {
}