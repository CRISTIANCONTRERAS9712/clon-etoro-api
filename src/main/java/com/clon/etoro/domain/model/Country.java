package com.clon.etoro.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

	private Long id;
	private String iso;
	private String name;
	private Boolean active;

	public Country(Long id) {
		super();
		this.id = id;
	}

	public Country(String iso) {
		super();
		this.iso = iso;
	}
}
