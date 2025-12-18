package com.clon.etoro.infraestructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("account")
public class AccountEntity {
    @Id
    private Long id;
    private Long userId;
    private Double cashAvailable;
}
