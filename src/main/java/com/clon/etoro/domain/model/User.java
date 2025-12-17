package com.clon.etoro.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class User {
	private Long idUser;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate birthdate;
	private String password;
	private String cellphone;
	private Country country;

	public User() {
		super();
	}

	public User(Long idUser, String firstname, String lastname, String email, LocalDate birthdate, String password,
			String cellphone, Country country) {
		super();
		this.idUser = idUser;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthdate = birthdate;
		this.password = password;
		this.cellphone = cellphone;
		this.country = country;
	}
}
