package com.capgemini.parallel.beans;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
@Embeddable
public class Wallet {
	private BigDecimal balance;

	public Wallet(BigDecimal amount) {
		this.balance=amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	

	public Wallet() {
		super();
	}

	@Override
		public String toString() {
		return ", balance="+balance;
	}
}
