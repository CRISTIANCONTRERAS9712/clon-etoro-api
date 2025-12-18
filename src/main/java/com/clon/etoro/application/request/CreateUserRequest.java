package com.clon.etoro.application.request;

import java.time.LocalDate;

public record CreateUserRequest(String firstname, String lastname, String email, LocalDate birthday,
		String password, String cellphone, String isoCountry) {
}