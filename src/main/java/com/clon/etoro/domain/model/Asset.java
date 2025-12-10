package com.clon.etoro.domain.model;

import lombok.Data;

@Data
public class Asset {
	private Long idAsset;
	private String ticker;
	private String name;
	private String description;
	private String logo;
	private String price;
}
