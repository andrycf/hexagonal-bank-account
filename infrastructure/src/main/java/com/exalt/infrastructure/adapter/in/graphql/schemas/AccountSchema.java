package com.exalt.infrastructure.adapter.in.graphql.schemas;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.service.AccountService;

@Controller
public class AccountSchema {
    private final AccountService accountService;

    public AccountSchema(AccountService accountService) {
        this.accountService = accountService;
    }

    @QueryMapping
    public AccountDTO getBalance(@Argument String accountNumber) {
        return accountService.checkBalance(accountNumber);
    }

    @MutationMapping
    public AccountDTO create(@Argument AccountDTO account) {
        return accountService.createAccount(account);
    }
}
