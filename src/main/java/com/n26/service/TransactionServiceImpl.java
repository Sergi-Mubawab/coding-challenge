package com.n26.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.n26.constants.Constants;
import com.n26.domain.Transaction;
import com.n26.dto.GetResponseDto;
import com.n26.dto.TransactionDto;
import com.n26.repository.TransactionRepository;
import com.n26.utils.DateUtils;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public TransactionDto update(TransactionDto transaction) {
		Transaction transactionToUpdate = new Transaction(transaction.getAmount(), transaction.getTimestamp());
		Transaction transactionUpdated = transactionRepository.update(transactionToUpdate);
		return new TransactionDto(transactionUpdated.getAmount(), transactionUpdated.getTimestamp());
	}

	@Override
	public GetResponseDto readTotalInformation() {
		List<Transaction> transactions = transactionRepository.readRecent();

		GetResponseDto response = new GetResponseDto();

		if (!CollectionUtils.isEmpty(transactions)) {
			response.setCount(transactions.size());

			BigDecimal sum = BigDecimal.valueOf(0);
			BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);
			BigDecimal min = BigDecimal.valueOf(Double.MAX_VALUE);

			for (Transaction transaction : transactions) {
				sum = sum.add(transaction.getAmount());
				max = max.max(transaction.getAmount());
				min = min.min(transaction.getAmount());
			}

			response.setSum(sum.toString());
			response.setMax(max.toString());
			response.setMin(min.toString());
			response.setAvg(sum.divide(BigDecimal.valueOf(response.getCount()), Constants.ACCEPTED_DECIMALS,
					BigDecimal.ROUND_HALF_UP).toString());
		}

		return response;
	}

	@Override
	public void deleteAll() {
		transactionRepository.deleteAll();
	}

	@Override
	public boolean isTooOld(TransactionDto transaction) {
		return transaction.getTimestamp().before(DateUtils.secondsAgo(Constants.ACCEPTED_SECONDS));
	}

	@Override
	public boolean isFuturistic(TransactionDto transaction) {
		return transaction.getTimestamp().after(DateUtils.today());
	}

}