package com.exalt.infrastructure.adapter.out.postgres;

import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.infrastructure.adapter.out.postgres.mapper.AccountEntityMapperAdapter;
import com.exalt.infrastructure.adapter.out.postgres.mapper.TransactionEntityMapperAdapter;
import com.exalt.infrastructure.adapter.out.postgres.repository.AccountRepository;
import com.exalt.infrastructure.adapter.out.postgres.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankRepositoryAdapter implements BankRepositoryPort {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankRepositoryAdapter(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Account> findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(AccountEntityMapperAdapter::toMetier);
    }

    @Override
    public void saveAccount(Account newAccount) {
        accountRepository.save(AccountEntityMapperAdapter.toEntity(newAccount));
    }

    @Override
    public void saveTransaction(Transaction newTransaction) {
        transactionRepository.save(TransactionEntityMapperAdapter.toEntity(newTransaction));
    }

    @Override
    public List<Transaction> findAllTransactionByAccountNumber(String accountNumber) {
        return transactionRepository.findAllByAccountNumber(accountNumber)
                .stream()
                .map(TransactionEntityMapperAdapter::toMetier)
                .toList();
    }
}
