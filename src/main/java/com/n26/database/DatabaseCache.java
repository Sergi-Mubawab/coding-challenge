package com.n26.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.n26.domain.Transaction;

public class DatabaseCache implements Database {

	private static final int TIME_EXPIRATION = 60;

	Cache<Transaction, Date> transactions = CacheBuilder.newBuilder()
			.expireAfterWrite(TIME_EXPIRATION, TimeUnit.SECONDS)
			.build();

	@Override
	public Transaction put(Transaction transaction) {
		transactions.put(transaction, transaction.getTimestamp());
		return transaction;
	}

	@Override
	public List<Transaction> readAll() {
		ConcurrentMap<Transaction, Date> allEntries = transactions.asMap();
		return new ArrayList<>(allEntries.keySet());
	}

	@Override
	public void delete() {
		transactions.invalidateAll();
	}

}
