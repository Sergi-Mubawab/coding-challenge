package com.n26.controller;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.constants.Constants;
import com.n26.domain.Transaction;
import com.n26.repository.TransactionRepositoryImpl;

@SpringBootTest
public class TestTransactionRepository {

	@InjectMocks
	private TransactionRepositoryImpl transactionRepository;

	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdate() {
		Transaction expectedEntity = new Transaction(
				BigDecimal.TEN.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP), new Date());
		Transaction expected = new Transaction(
				BigDecimal.TEN.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP), new Date());

		Transaction result = transactionRepository.update(expected);

		boolean isValid = false;

		Assertions.assertTrue(isValid, "Transaction updated correctly");
	}

}