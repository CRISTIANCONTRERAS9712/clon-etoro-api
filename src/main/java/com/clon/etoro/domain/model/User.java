package com.clon.etoro.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate birthdate;
	private String password;
	private String cellphone;
	private Country country;
}
