package com.clon.etoro.domain.model;

public class Account {
	private Long idAccount;
	private User user;
	private Double cashAvailable;
	
	public Account() {
		super();
	}

	public Account(Long idAccount, User user, Double cashAvailable) {
		super();
		if (cashAvailable < 0)
            throw new IllegalArgumentException("cashAvailable must be positive");
		this.idAccount = idAccount;
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

	public Long getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Long idAccount) {
		this.idAccount = idAccount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getCashAvailable() {
		return cashAvailable;
	}

	public void setCashAvailable(Double cashAvailable) {
		this.cashAvailable = cashAvailable;
	}
}
