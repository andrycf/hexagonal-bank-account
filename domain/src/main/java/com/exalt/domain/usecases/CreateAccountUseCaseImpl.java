package com.exalt.domain.usecases;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.api.CreateAccountUseCase;
import com.exalt.domain.ports.spi.BankRepositoryPort;

import java.time.LocalDateTime;

@DomainService
public final class CreateAccountUseCaseImpl implements CreateAccountUseCase {
    private final BankRepositoryPort bankRepositoryPort;

    public CreateAccountUseCaseImpl(BankRepositoryPort bankRepositoryPort) {
        this.bankRepositoryPort = bankRepositoryPort;
    }

    @Override
    public Account createAccount(Account account) {
        if(account == null) throw new BankException("Error created account null");
        else {
            if(account.accountNumber() == null || account.accountNumber().isBlank()) throw new BankException("Error created account with AccountNumber Null or Empty");
            try{
                bankRepositoryPort.findAccountByAccountNumber(account.accountNumber())
                        .ifPresent(accountExist -> {
                            throw new BankException("Error created account with AccountNumber already exist");
                        });
                var newTransaction = new Transaction(account.accountNumber(), LocalDateTime.now(),account.balance());
                bankRepositoryPort.saveTransaction(newTransaction);
                bankRepositoryPort.saveAccount(account);
            }catch (BankException e){
                throw e;
            }catch (Exception e){
                throw new BankException("Error created account from Database");
            }
        }
        return account;
    }
}
