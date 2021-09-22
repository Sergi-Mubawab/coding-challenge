package com.n26.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.n26.constants.Constants;
import com.n26.database.Database;
import com.n26.database.DatabaseCache;
import com.n26.domain.Transaction;
import com.n26.utils.DateUtils;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	private Database db = new DatabaseCache();

	@Override
	public Transaction update(Transaction transaction) {
		db.put(transaction);
		return transaction;
	}

	@Override
	public List<Transaction> readRecent() {
		List<Transaction> transactions = db.readAll();
		return transactions.stream()
				.filter(transaction -> transaction.getTimestamp()
						.after(DateUtils.secondsAgo(Constants.ACCEPTED_SECONDS)))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAll() {
		db.delete();
	}
}
