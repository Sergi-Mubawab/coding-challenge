package com.n26.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Transaction {

	private BigDecimal amount;
	private Date timestamp;

	public Transaction(BigDecimal amount, Date timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

}