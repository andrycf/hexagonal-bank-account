package com.exalt.infrastructure.adapter.in.rest.mapper;

import java.util.List;

import com.exalt.domain.model.Transaction;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;

public class TransactionDTOMapperAdapter {

    public static List<TransactionResponse> toDTO(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> new TransactionResponse(transaction.timestamp(), transaction.amount())).toList();
    }
}
