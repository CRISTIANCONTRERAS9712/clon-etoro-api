package com.clon.etoro.infraestructure.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("users") // nombre en la BD
public class UserEntity {
	@Id
	private Long idUser;
	private String firstname;
	private String lastname;
	private String email;
	private LocalDate birthdate;
	private String password;
	private String cellphone;
	private Long countryId;
	
	
}
