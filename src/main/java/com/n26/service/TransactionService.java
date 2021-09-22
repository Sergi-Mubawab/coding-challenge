package com.n26.service;

import com.n26.dto.GetResponseDto;
import com.n26.dto.TransactionDto;

public interface TransactionService {

	TransactionDto update(TransactionDto transaction);

	boolean isTooOld(TransactionDto transaction);

	boolean isFuturistic(TransactionDto transaction);

	GetResponseDto readTotalInformation();

	void deleteAll();

}
