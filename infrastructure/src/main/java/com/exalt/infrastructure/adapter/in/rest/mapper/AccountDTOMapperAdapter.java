package com.exalt.infrastructure.adapter.in.rest.mapper;

import com.exalt.domain.model.Account;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;

public class AccountDTOMapperAdapter {

    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(account.accountNumber(), account.balance());
    }
}
