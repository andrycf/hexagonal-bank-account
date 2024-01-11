package com.exalt.infrastructure.adapter.in.rest.mapper;

import com.exalt.domain.model.Transaction;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;

import java.util.List;


public class TransactionDTOMapperAdapter {

    public static List<TransactionResponse> toDTO(List<Transaction> transactions){
        return transactions.stream().map(transaction -> new TransactionResponse(transaction.timestamp(),transaction.amount())).toList();
    }
}
