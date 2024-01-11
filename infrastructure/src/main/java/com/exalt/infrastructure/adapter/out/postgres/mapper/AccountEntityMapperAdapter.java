package com.exalt.infrastructure.adapter.out.postgres.mapper;

import com.exalt.domain.model.Account;
import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;

public class AccountEntityMapperAdapter {
    public static Account toMetier(AccountEntity accountEntity){
        return new Account(accountEntity.getAccountNumber(), accountEntity.getBalance());
    }
    public static AccountEntity toEntity(Account account){
        return new AccountEntity(account.accountNumber(), account.balance());
    }
}
