package com.exalt.domain.ports.api;

import com.exalt.domain.model.Account;

public interface DepositAmountUseCase {
    Account depositAmount(String accountNumber, double amount);
}
