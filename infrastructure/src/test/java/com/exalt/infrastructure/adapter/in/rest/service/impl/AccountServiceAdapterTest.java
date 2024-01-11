package com.exalt.infrastructure.adapter.in.rest.service.impl;

import com.exalt.domain.model.Account;
import com.exalt.domain.ports.api.CheckBalanceUseCase;
import com.exalt.domain.ports.api.CreateAccountUseCase;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceAdapterTest {
    @InjectMocks
    private AccountServiceAdapter accountService;
    @Mock
    private CheckBalanceUseCase checkBalanceUseCase;
    @Mock
    private CreateAccountUseCase createAccountUseCase;
    @Test
    void checkBalance() {
        //GIVEN
        var accountNumber = "1213";
        var balance = 100;
        //WHEN
        Mockito.when(
                checkBalanceUseCase.checkBalance(accountNumber)
        ).thenReturn(Double.valueOf(balance));
        var account = accountService.checkBalance(accountNumber);
        //THEN
        Assertions.assertEquals(
                new AccountDTO(accountNumber,balance),
                account
        );
    }

    @Test
    void createAccount() {
        //GIVEN
        var accountNumber = "1213";
        var balance = 100;
        Mockito.when(
                createAccountUseCase.createAccount(new Account(accountNumber,balance))
        ).thenReturn(new Account(accountNumber,balance));
        var account = accountService.createAccount(
                new AccountDTO(accountNumber,balance)
        );
        Assertions.assertEquals(
                new AccountDTO(accountNumber,balance),
                account
        );
    }
}