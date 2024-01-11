package com.exalt.domain.ports.api;

import com.exalt.domain.model.Account;

public interface CreateAccountUseCase {
    Account createAccount(Account account);
}
