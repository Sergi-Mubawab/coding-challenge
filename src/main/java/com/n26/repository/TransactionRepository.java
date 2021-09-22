package com.n26.repository;

import java.util.List;

import com.n26.domain.Transaction;

public interface TransactionRepository {
	Transaction update(Transaction transaction);

	List<Transaction> readRecent();

	void deleteAll();

}
