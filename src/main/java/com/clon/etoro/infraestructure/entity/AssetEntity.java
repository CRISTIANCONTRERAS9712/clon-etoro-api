package com.clon.etoro.infraestructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("asset")
public class AssetEntity {
    @Id
    private Long id;
    private String ticker;
    private String name;
    private String description;
    private String logo;
    private String price;
    private Boolean active;
}
