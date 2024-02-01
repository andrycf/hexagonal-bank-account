package com.exalt.infrastructure.adapter.in.graphql.schemas;


import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.exalt.infrastructure.adapter.in.rest.service.TransactionService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionSchema {
    private final TransactionService transactionService;

    public TransactionSchema(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<TransactionResponse> getTransactionHistory(@Argument String accountNumber){
        return transactionService.checkTransactionHistory(accountNumber);
    }

    @MutationMapping
    public AccountDTO deposit(@Argument TransactionRequest transaction){
        return transactionService.depositAmount(transaction);
    }

    @MutationMapping
    public AccountDTO withdraw(@Argument TransactionRequest transaction){
        return transactionService.withdrawAmount(transaction);
    }
}
