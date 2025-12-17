package com.clon.etoro.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
	private Long id;
	private User user;
	private Double cashAvailable;

	public Account(Long id, User user, Double cashAvailable) {
		super();
		if (cashAvailable < 0)
			throw new IllegalArgumentException("cashAvailable must be positive");
		this.id = id;
		this.user = user;
		this.cashAvailable = cashAvailable;
	}

	public void loadCash(Double amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Amount must be positive");
		cashAvailable = Double.sum(cashAvailable, amount);
	}

	public Account subtract(Double amount) {
		if (amount > cashAvailable)
			throw new IllegalArgumentException("Insufficient cash");
		return this;
	}
}
