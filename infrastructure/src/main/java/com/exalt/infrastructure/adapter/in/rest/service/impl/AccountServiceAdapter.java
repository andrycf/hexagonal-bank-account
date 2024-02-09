package com.exalt.infrastructure.adapter.in.rest.service.impl;

import org.springframework.stereotype.Service;

import com.exalt.domain.model.Account;
import com.exalt.domain.ports.api.CheckBalanceUseCase;
import com.exalt.domain.ports.api.CreateAccountUseCase;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.mapper.AccountDTOMapperAdapter;
import com.exalt.infrastructure.adapter.in.rest.service.AccountService;

@Service
public class AccountServiceAdapter implements AccountService {
    private final CheckBalanceUseCase checkBalanceUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    public AccountServiceAdapter(CheckBalanceUseCase checkBalanceUseCase, CreateAccountUseCase createAccountUseCase) {
        this.checkBalanceUseCase = checkBalanceUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    @Override
    public AccountDTO checkBalance(String accountNumber) {
        var balance = checkBalanceUseCase.checkBalance(accountNumber);
        return new AccountDTO(accountNumber, balance);
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        var account = createAccountUseCase.createAccount(new Account(accountDTO.accountNumber(), accountDTO.balance()));
        return AccountDTOMapperAdapter.toDTO(account);
    }

}
