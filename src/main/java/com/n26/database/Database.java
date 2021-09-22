package com.n26.database;

import java.util.List;

import com.n26.domain.Transaction;

public interface Database {

	Transaction put(Transaction transaction);

	List<Transaction> readAll();

	void delete();

}
