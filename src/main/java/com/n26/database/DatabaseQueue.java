package com.n26.database;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

import com.n26.domain.Transaction;

public class DatabaseQueue implements Database {

	BlockingQueue<DelayedObject> transactions = new DelayQueue<>();

	@Override
	public Transaction put(Transaction transaction) {
		transactions.add(new DelayedObject(transaction));
		return transaction;
	}

	@Override
	public List<Transaction> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
