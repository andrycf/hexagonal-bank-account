package com.exalt.domain.ports.api;

import com.exalt.domain.model.Account;

public interface WithdrawAmountUseCase {
    Account withdrawAmount(String accountNumber,double amount);
}
