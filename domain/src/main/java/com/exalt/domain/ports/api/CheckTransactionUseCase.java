package com.exalt.domain.ports.api;

import com.exalt.domain.model.Transaction;

import java.util.List;

public interface CheckTransactionUseCase {
    List<Transaction> checkTransactionHistory(String accountNumber);
}
