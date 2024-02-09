package com.exalt.infrastructure.adapter.in.rest.service;

import java.util.List;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;

public interface TransactionService {
    AccountDTO depositAmount(TransactionRequest transactionRequest);

    List<TransactionResponse> checkTransactionHistory(String accountNumber);

    AccountDTO withdrawAmount(TransactionRequest transactionRequest);
}
