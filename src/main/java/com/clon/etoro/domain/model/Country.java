package com.clon.etoro.domain.model;

import lombok.Data;

@Data
public class Country {

	private Long idCountry;
	private String isoCountry;
	private String name;
	private Boolean active;

	public Country() {
		super();
	}

	public Country(Long idCountry) {
		super();
		this.idCountry = idCountry;
	}

	public Country(String isoCountry) {
		super();
		this.isoCountry = isoCountry;
	}

	public Country(Long idCountry, String isoCountry, String name, Boolean active) {
		super();
		this.idCountry = idCountry;
		this.isoCountry = isoCountry;
		this.name = name;
		this.active = active;
	}

	public Long getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(Long idCountry) {
		this.idCountry = idCountry;
	}

	public String getIsoCountry() {
		return isoCountry;
	}

	public void setIsoCountry(String isoCountry) {
		this.isoCountry = isoCountry;
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
