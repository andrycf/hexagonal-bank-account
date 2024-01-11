package com.exalt.domain.ports.spi.stubs;

import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.spi.BankRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankRepositoryPortStub implements BankRepositoryPort {
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private final String accountNumberErrorFind = "00000";
    private final String accountNumberErrorSave = "1111";
    @Override
    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        if(accountNumber.equals(accountNumberErrorFind)) throw new RuntimeException("Error Database");
        return accounts.stream().filter(
                account ->
                        account.accountNumber().equals(accountNumber))
                .findAny();
    }

    @Override
    public void saveAccount(Account account) {
        if(account.accountNumber().equals(accountNumberErrorSave)) throw new RuntimeException("Error Database");
        accounts.stream()
                .filter(accountExist -> account.accountNumber().equals(accountExist.accountNumber()))
                .findAny().ifPresent(account1 -> {
                    accounts.remove(account1);
                });
        accounts.add(account);
    }

    @Override
    public void saveTransaction(Transaction newTransaction) {
        transactions.add(newTransaction);
    }

    @Override
    public List<Transaction> findAllTransactionByAccountNumber(String accountNumber) {
        if(accountNumber.equals(accountNumberErrorFind)) throw new RuntimeException("Error Database");
        return transactions.stream().filter(account ->
                account.accountNumber().equals(accountNumber)).toList();
    }
}
