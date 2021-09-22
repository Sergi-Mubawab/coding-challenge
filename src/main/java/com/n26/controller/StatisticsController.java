package com.n26.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.dto.GetResponseDto;
import com.n26.service.TransactionService;

@RestController
public class StatisticsController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping(path = "/statistics")
	public ResponseEntity<GetResponseDto> get() {
		return new ResponseEntity<>(transactionService.readTotalInformation(), HttpStatus.OK);
	}

}