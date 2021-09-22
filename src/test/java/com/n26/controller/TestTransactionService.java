package com.n26.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.constants.Constants;
import com.n26.domain.Transaction;
import com.n26.dto.GetResponseDto;
import com.n26.dto.TransactionDto;
import com.n26.repository.TransactionRepository;
import com.n26.service.TransactionServiceImpl;
import com.n26.utils.DateUtils;

@SpringBootTest
public class TestTransactionService {

	// @Mock
	@Spy
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdate() {
		Transaction expectedEntity = new Transaction(
				BigDecimal.TEN.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP), new Date());
		TransactionDto expectedDto = new TransactionDto(
				BigDecimal.TEN.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP), new Date());

		doReturn(expectedEntity).when(transactionRepository).update(ArgumentMatchers.any(Transaction.class));

		TransactionDto resultDto = transactionService.update(expectedDto);

		verify(transactionRepository, times(1)).update(ArgumentMatchers.any(Transaction.class));

		boolean isValid = expectedDto.getAmount().equals(resultDto.getAmount());
		isValid &= expectedDto.getTimestamp().equals(resultDto.getTimestamp());

		Assertions.assertTrue(isValid, "Transaction updated correctly");
	}

	@Test
	public void testTotalInformationNoInfo() {
		GetResponseDto infoExpected = new GetResponseDto();
		infoExpected.setAvg("0.00");
		infoExpected.setSum("0.00");
		infoExpected.setCount(0);
		infoExpected.setMin("0.00");
		infoExpected.setMax("0.00");

		List<Transaction> transactions = new ArrayList<>();
		doReturn(transactions).when(transactionRepository).readRecent();

		GetResponseDto infoReturned = transactionService.readTotalInformation();
		boolean isValid = infoExpected.getAvg().equals(infoReturned.getAvg());
		isValid &= infoExpected.getSum().equals(infoReturned.getSum());
		isValid &= infoExpected.getCount() == infoReturned.getCount();
		isValid &= infoExpected.getMin().equals(infoReturned.getMin());
		isValid &= infoExpected.getMax().equals(infoReturned.getMax());

		verify(transactionRepository).readRecent();

		Assertions.assertNotNull(infoReturned);
		Assertions.assertTrue(isValid, "Statistics are correct");
	}

	@Test
	public void testTotalInformationSomeInfo() {
		GetResponseDto infoExpected = new GetResponseDto();
		infoExpected.setAvg("5.00");
		infoExpected.setSum("10.00");
		infoExpected.setCount(2);
		infoExpected.setMin("0.00");
		infoExpected.setMax("10.00");

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(BigDecimal.TEN.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP),
				new Date()));
		transactions.add(new Transaction(
				BigDecimal.ZERO.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP), new Date()));

		doReturn(transactions).when(transactionRepository).readRecent();

		GetResponseDto infoReturned = transactionService.readTotalInformation();
		boolean isValid = infoExpected.getAvg().equals(infoReturned.getAvg());
		isValid &= infoExpected.getSum().equals(infoReturned.getSum());
		isValid &= infoExpected.getCount() == infoReturned.getCount();
		isValid &= infoExpected.getMin().equals(infoReturned.getMin());
		isValid &= infoExpected.getMax().equals(infoReturned.getMax());

		InOrder inOrder = inOrder(transactionRepository);
		inOrder.verify(transactionRepository).readRecent();

		Assertions.assertTrue(isValid, "Statistics are correct");
	}

	@Test
	public void testDeleteAll() {
		doNothing().when(transactionRepository).deleteAll();
		transactionService.deleteAll();

		verify(transactionRepository).deleteAll();

		Assertions.assertTrue(true, "No exceptions found in delete");
	}

	@Test
	public void testDateTooOld() {
		TransactionDto transaction = new TransactionDto(BigDecimal.valueOf(10), DateUtils.secondsAgo(120));

		boolean expected = true;
		boolean result = transactionService.isTooOld(transaction);

		Assertions.assertEquals(expected, result, "Date is too old");
	}

	@Test
	public void testDateRecentOld() {
		TransactionDto transaction = new TransactionDto(BigDecimal.valueOf(10), DateUtils.secondsAgo(40));

		boolean expected = false;
		boolean result = transactionService.isTooOld(transaction);

		Assertions.assertEquals(expected, result, "Date is recent");
	}

	@Test
	public void testDateNotFuturistic() {
		TransactionDto transaction = new TransactionDto(BigDecimal.valueOf(10), DateUtils.secondsAgo(40));

		boolean expected = false;
		boolean result = transactionService.isFuturistic(transaction);

		Assertions.assertEquals(expected, result, "Date is not futuristic");
	}

	@Test
	public void testDateTooFuturistic() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.SECOND, (calendar.get(Calendar.SECOND) + 100));

		TransactionDto transaction = new TransactionDto(BigDecimal.valueOf(10), calendar.getTime());

		boolean expected = true;
		boolean result = transactionService.isFuturistic(transaction);

		Assertions.assertEquals(expected, result, "Date is futuristic");
	}

}