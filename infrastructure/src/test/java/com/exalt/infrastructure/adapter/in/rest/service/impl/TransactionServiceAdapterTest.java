package com.exalt.infrastructure.adapter.in.rest.service.impl;

import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.api.CheckTransactionUseCase;
import com.exalt.domain.ports.api.DepositAmountUseCase;
import com.exalt.domain.ports.api.WithdrawAmountUseCase;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceAdapterTest {

    @InjectMocks
    TransactionServiceAdapter transactionServiceAdapter;
    @Mock
    private WithdrawAmountUseCase withdrawAmountUseCase;
    @Mock
    private DepositAmountUseCase depositAmountUseCase;
    @Mock
    private CheckTransactionUseCase checkTransactionUseCase;

    @Test
    void depositAmount() {
        var accountNumber = "1243";
        var amount = 100;
        Mockito.when(
                depositAmountUseCase.depositAmount(
                        accountNumber,
                        amount
                )
        ).thenReturn(new Account(accountNumber,amount));
        var reponse = transactionServiceAdapter.depositAmount(
                new TransactionRequest(accountNumber,amount)
        );
        Assertions.assertEquals(
                new AccountDTO(accountNumber,amount),
                reponse
        );
    }

    @Test
    void checkTransactionHistory() {
        var accountNumber = "1243";
        var timestamp = LocalDateTime.now();
        var amount = 100;
        Mockito.when(
                checkTransactionUseCase.checkTransactionHistory(accountNumber)
        ).thenReturn(List.of(
                new Transaction(accountNumber, timestamp,amount),
                new Transaction(accountNumber, timestamp,-amount)
        ));
        var result = transactionServiceAdapter.checkTransactionHistory(accountNumber);
        Assertions.assertEquals(2,result.size());
    }

    @Test
    void withdrawAmount() {
        var accountNumber = "1243";
        var amount = 100;
        Mockito.when(
                withdrawAmountUseCase.withdrawAmount(
                        accountNumber,
                        amount
                )
        ).thenReturn(new Account(accountNumber,amount));
        var reponse = transactionServiceAdapter.withdrawAmount(
                new TransactionRequest(accountNumber,amount)
        );
        Assertions.assertEquals(
                new AccountDTO(accountNumber,amount),
                reponse
        );
    }
}