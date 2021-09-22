package com.n26.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.n26.domain.Transaction;

public class DatabaseMap implements Database {
	private WeakConcurrentHashMap<Long, Transaction> transactions = new WeakConcurrentHashMap<>();
	private AtomicLong ai = new AtomicLong(-1);

	@Override
	public Transaction put(Transaction transaction) {
		return transactions.put(ai.incrementAndGet(), transaction);
	}

	@Override
	public List<Transaction> readAll() {
		List<Transaction> transactionsToReturn = new ArrayList<>();
		transactions.forEach((k, v) -> transactionsToReturn.add(v));
		return transactionsToReturn;
	}

	@Override
	public void delete() {
		transactions.clear();
	}
}
