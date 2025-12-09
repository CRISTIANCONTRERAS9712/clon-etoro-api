package com.clon.etoro.infraestructure.entity;

import java.time.LocalDate;

public class UserEntity {
	private Long idUsuario;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate birthdate;
	private String password;
	private String cellphone;

	public UserEntity() {
		super();
	}

	public UserEntity(
			Long idUsuario, 
			String firstname, 
			String lastname, 
			String email, 
			LocalDate birthdate,
			String password, 
			String cellphone
		) {
		super();
		this.idUsuario = idUsuario;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthdate = birthdate;
		this.password = password;
		this.cellphone = cellphone;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
}
