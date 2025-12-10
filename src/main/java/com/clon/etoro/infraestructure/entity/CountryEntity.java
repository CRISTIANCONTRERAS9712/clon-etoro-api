package com.clon.etoro.infraestructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("country") // nombre en la BD
public class CountryEntity {
	@Id
	private Long idCountry;
	private String isoCountry;
	private String name;
	private Boolean active;
}
