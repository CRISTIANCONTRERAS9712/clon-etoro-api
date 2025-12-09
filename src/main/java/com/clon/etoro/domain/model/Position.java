package com.clon.etoro.domain.model;

import java.time.LocalDateTime;

public class Position {
	private Long idPosition;
	private User user;
	private Asset asset;
	private Double units;
	private Double buyPrice;
	private LocalDateTime buyDate;
}
