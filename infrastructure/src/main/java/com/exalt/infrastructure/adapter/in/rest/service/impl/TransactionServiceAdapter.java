package com.exalt.infrastructure.adapter.in.rest.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exalt.domain.ports.api.CheckTransactionUseCase;
import com.exalt.domain.ports.api.DepositAmountUseCase;
import com.exalt.domain.ports.api.WithdrawAmountUseCase;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionResponse;
import com.exalt.infrastructure.adapter.in.rest.mapper.AccountDTOMapperAdapter;
import com.exalt.infrastructure.adapter.in.rest.mapper.TransactionDTOMapperAdapter;
import com.exalt.infrastructure.adapter.in.rest.service.TransactionService;

@Service
@Transactional
public class TransactionServiceAdapter implements TransactionService {
    private final WithdrawAmountUseCase withdrawAmountUseCase;
    private final DepositAmountUseCase depositAmountUseCase;
    private final CheckTransactionUseCase checkTransactionUseCase;

    public TransactionServiceAdapter(WithdrawAmountUseCase withdrawAmountUseCase,
            DepositAmountUseCase depositAmountUseCase, CheckTransactionUseCase checkTransactionUseCase) {
        this.withdrawAmountUseCase = withdrawAmountUseCase;
        this.depositAmountUseCase = depositAmountUseCase;
        this.checkTransactionUseCase = checkTransactionUseCase;
    }

    @Override
    public AccountDTO depositAmount(TransactionRequest transactionRequest) {
        var account = depositAmountUseCase.depositAmount(transactionRequest.accountNumber(),
                transactionRequest.amount());
        return AccountDTOMapperAdapter.toDTO(account);
    }

    @Override
    public List<TransactionResponse> checkTransactionHistory(String accountNumber) {
        var transactions = checkTransactionUseCase.checkTransactionHistory(accountNumber);
        return TransactionDTOMapperAdapter.toDTO(transactions);
    }

    @Override
    public AccountDTO withdrawAmount(TransactionRequest transactionRequest) {
        var account = withdrawAmountUseCase.withdrawAmount(transactionRequest.accountNumber(),
                transactionRequest.amount());
        return AccountDTOMapperAdapter.toDTO(account);
    }
}
