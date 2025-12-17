package com.clon.etoro.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
	private Long id;
	private String ticker;
	private String name;
	private String description;
	private String logo;
	private String price;
	private Boolean active;
}
