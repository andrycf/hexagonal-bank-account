package com.exalt.infrastructure.adapter.in.graphql.schemas;


import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.exalt.infrastructure.adapter.in.rest.service.TransactionService;
import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;
import com.exalt.infrastructure.adapter.out.postgres.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionSchema {
    private final TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionSchema(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<TransactionResponse> getTransactionHistory(@Argument String accountNumber) {
        return transactionService.checkTransactionHistory(accountNumber);
    }

    @SchemaMapping(typeName = "Account",field = "getTransactionHistory")
    public List<TransactionResponse> getTransactionHistory(AccountDTO account) {
        return transactionService.checkTransactionHistory(account.accountNumber());
    }

    @SchemaMapping(typeName = "Account",field = "transactions")
    public Window<TransactionEntity> getTransactionHistory(AccountDTO account,ScrollSubrange scrollSubrange) {
        String accountNumber = account.accountNumber();
        ScrollPosition position = scrollSubrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(scrollSubrange.count().orElse(10));
        Sort sort = Sort.by("accountNumber").ascending();
        return transactionRepository.findByAccountNumber(accountNumber, position, limit, sort);
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
