package com.n26.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 2246546699636019003L;

	private BigDecimal amount;
	private Date timestamp;

	public TransactionDto(BigDecimal amount, Date timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
	}

}