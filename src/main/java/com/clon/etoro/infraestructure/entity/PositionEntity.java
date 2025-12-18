package com.clon.etoro.infraestructure.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("position")
public class PositionEntity {
	@Id
	private Long id;
	private Long userId;
	private Long assetId;
	private Double units;
	private Double buyPrice;
	private LocalDateTime buyDate;
}
