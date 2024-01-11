package com.exalt.domain.usecases;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Transaction;
import com.exalt.domain.ports.api.CheckTransactionUseCase;
import com.exalt.domain.ports.spi.BankRepositoryPort;

import java.util.List;

@DomainService
public final class CheckTransactionUseCaseImpl implements CheckTransactionUseCase {
    private final BankRepositoryPort bankRepositoryPort;;

    public CheckTransactionUseCaseImpl(BankRepositoryPort bankRepositoryPort) {
        this.bankRepositoryPort = bankRepositoryPort;
    }

    @Override
    public List<Transaction> checkTransactionHistory(String accountNumber) {
        if(accountNumber == null || accountNumber.isBlank())
            throw new BankException("Error check transaction with AccountNumber Null or Empty");
        try {
            return bankRepositoryPort.findAllTransactionByAccountNumber(accountNumber);
        }catch (Exception e){
            throw new BankException("Error check transaction from Database Error");
        }
    }
}
