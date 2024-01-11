package com.exalt.infrastructure.adapter.in.rest.service;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;

public interface AccountService {
    AccountDTO checkBalance(String accountNumber);
    AccountDTO createAccount(AccountDTO accountDTO);
}
