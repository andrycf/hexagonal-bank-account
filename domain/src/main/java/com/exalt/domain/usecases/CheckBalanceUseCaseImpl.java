package com.exalt.domain.usecases;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.ports.api.CheckBalanceUseCase;
import com.exalt.domain.ports.spi.BankRepositoryPort;

import java.util.concurrent.atomic.AtomicReference;

@DomainService
public final class CheckBalanceUseCaseImpl implements CheckBalanceUseCase {
    private final BankRepositoryPort bankRepositoryPort;

    public CheckBalanceUseCaseImpl(BankRepositoryPort bankRepositoryPort) {
        this.bankRepositoryPort = bankRepositoryPort;
    }

    @Override
    public double checkBalance(String accountNumber) {
        AtomicReference<Double> balance = new AtomicReference<>((double) 0);
        if(accountNumber == null || accountNumber.isBlank())
            throw new BankException("Error check balance with AccountNumber Null or Empty");
        try {
            bankRepositoryPort.findAccountByAccountNumber(accountNumber)
                    .ifPresentOrElse(account -> {
                        balance.set(account.balance());
                    },() -> {
                        throw new BankException("Error check balance with AccountNumber not found");
                    });
        }catch (BankException e){
            throw e;
        }catch (Exception e){
            throw new BankException("Error check balance from Database");
        }
       return balance.get();
    }
}
