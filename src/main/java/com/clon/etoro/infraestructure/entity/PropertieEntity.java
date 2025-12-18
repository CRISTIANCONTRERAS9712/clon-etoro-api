package com.clon.etoro.infraestructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("propertie")
public class PropertieEntity {
	@Id
	private Long id;
	private String key;
	private String value;
	private String description;
}
