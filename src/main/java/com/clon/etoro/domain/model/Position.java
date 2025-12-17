package com.clon.etoro.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Position {
	private Long id;
	private User user;
	private Asset asset;
	private Double units;
	private Double buyPrice;
	private LocalDateTime buyDate;
}
