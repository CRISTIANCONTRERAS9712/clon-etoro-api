package com.clon.etoro.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {

	private Long id;
	private String iso;
	private String name;
	private Boolean active;

	public Country() {
		super();
	}

	public Country(Long id) {
		super();
		this.id = id;
	}

	public Country(String iso) {
		super();
		this.iso = iso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
