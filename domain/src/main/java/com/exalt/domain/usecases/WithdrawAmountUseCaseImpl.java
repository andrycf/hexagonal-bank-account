package com.exalt.domain.usecases;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.api.WithdrawAmountUseCase;
import com.exalt.domain.ports.spi.BankRepositoryPort;

import java.time.LocalDateTime;

@DomainService
public final class WithdrawAmountUseCaseImpl implements WithdrawAmountUseCase {
    private final BankRepositoryPort bankRepositoryPort;

    public WithdrawAmountUseCaseImpl(BankRepositoryPort bankRepositoryPort) {
        this.bankRepositoryPort = bankRepositoryPort;
    }

    @Override
    public Account withdrawAmount(String accountNumber, double amount) {
        try {
            validationInput(accountNumber, amount);
            var account = bankRepositoryPort.findAccountByAccountNumber(accountNumber)
                    .orElseThrow(() -> new BankException("Error withdraw amount with AccountNumber not found"));
            return saveNewAccountAndTransaction(accountNumber, amount, account);
        }catch (BankException e){
            throw e;
        }catch (Exception e){
            throw new BankException("Error withdraw amount from Database");
        }
    }

    private Account saveNewAccountAndTransaction(String accountNumber, double amount, Account account) {
        double balance = account.balance();
        if(balance < Math.abs(amount)) throw new BankException("Error withdraw account with Insufficient funds");
        double newBalance = balance + amount;
        var newAccount = new Account(accountNumber,newBalance);
        var newTransaction = new Transaction(
                newAccount.accountNumber(),
                LocalDateTime.now(),
                amount
        );
        bankRepositoryPort.saveTransaction(newTransaction);
        bankRepositoryPort.saveAccount(newAccount);
        return newAccount;
    }

    private void validationInput(String accountNumber, double amount) {
        if(accountNumber == null || accountNumber.isBlank())
            throw new BankException("Error withdraw amount with AccountNumber Null or Empty");
        if(amount >= 0)
            throw new BankException("Error withdraw amount greater than or equal to 0");
    }
}
