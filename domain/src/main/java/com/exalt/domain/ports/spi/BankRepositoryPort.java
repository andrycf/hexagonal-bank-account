package com.exalt.domain.ports.spi;

import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface BankRepositoryPort {
    Optional<Account> findAccountByAccountNumber(String accountNumber);

    void saveAccount(Account account);

    void saveTransaction(Transaction newTransaction);

    List<Transaction> findAllTransactionByAccountNumber(String accountNumber);
}
