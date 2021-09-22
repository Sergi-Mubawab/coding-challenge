package com.n26.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.constants.Constants;
import com.n26.dto.PostRequestDto;
import com.n26.dto.TransactionDto;
import com.n26.service.TransactionService;

@RestController
public class TransactionsController {

	private static final Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private TransactionService transactionService;

	@PostMapping(path = "/transactions")
	public ResponseEntity<String> post(@RequestBody PostRequestDto request) {
		HttpStatus responseCode = HttpStatus.FORBIDDEN;

		String amountParam = Optional.ofNullable(request.getAmount()).orElse("");
		String timestampParam = Optional.ofNullable(request.getTimestamp()).orElse("");

		if (StringUtils.isBlank(amountParam) || StringUtils.isBlank(timestampParam)) {
			responseCode = HttpStatus.BAD_REQUEST;
		} else {
			BigDecimal amount = null;
			Date timestamp = null;

			if (NumberUtils.isCreatable(amountParam)) {
				amount = new BigDecimal(amountParam);
				amount = amount.setScale(Constants.ACCEPTED_DECIMALS, BigDecimal.ROUND_HALF_UP);
			} else {
				responseCode = HttpStatus.UNPROCESSABLE_ENTITY;
			}

			try {
				timestamp = DateUtils.parseDate(timestampParam, new String[] { Constants.FULL_DATE });
			} catch (ParseException e) {
				logger.error("Error parsing date in post");
				responseCode = HttpStatus.UNPROCESSABLE_ENTITY;
			}

			if (amount != null && timestamp != null) {
				TransactionDto transaction = new TransactionDto(amount, timestamp);

				if (transactionService.isTooOld(transaction)) {
					responseCode = HttpStatus.NO_CONTENT;
				} else if (transactionService.isFuturistic(transaction)) {
					responseCode = HttpStatus.UNPROCESSABLE_ENTITY;
				} else {
					transactionService.update(transaction);
					responseCode = HttpStatus.CREATED;
				}
			}
		}

		return new ResponseEntity<>("", responseCode);
	}

	@DeleteMapping(path = "/transactions")
	public ResponseEntity<String> delete() {
		transactionService.deleteAll();
		return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
	}
}