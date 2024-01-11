package com.exalt.domain.ports.api;

import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.domain.ports.spi.stubs.BankRepositoryPortStub;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;
import com.exalt.domain.usecases.DepositAmountUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepositAmountUseCaseTest {
    private DepositAmountUseCase depositAmountUseCase;
    private CreateAccountUseCase createAccountUseCase;
    private BankRepositoryPort bankRepositoryPort;

    @BeforeEach
    void setUp(){
        bankRepositoryPort = new BankRepositoryPortStub();
        depositAmountUseCase = new DepositAmountUseCaseImpl(bankRepositoryPort);
        createAccountUseCase = new CreateAccountUseCaseImpl(bankRepositoryPort);
    }
    @Test
    void shouldExceptionDepositAmountWithAccountNumberNull() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            depositAmountUseCase.depositAmount(null,90);
        });
        //THEN
        Assertions.assertEquals("Error deposit amount with AccountNumber Null or Empty", thrown.getMessage());
    }
    @Test
    void shouldExceptionDepositAmountWithAccountNumberEmpty() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            depositAmountUseCase.depositAmount("   ",90);
        });
        //THEN
        Assertions.assertEquals("Error deposit amount with AccountNumber Null or Empty", thrown.getMessage());
    }
    @Test
    void shouldExceptionDepositAmountNegative() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            depositAmountUseCase.depositAmount("12121",-90);
        });
        //THEN
        Assertions.assertEquals("Error deposit amount less than or equal to 0", thrown.getMessage());
    }

    @Test
    void shouldDepositAmount() {
        //GIVE
        //WHEN
        bankRepositoryPort.saveAccount(new Account("2933",90));
        var account = depositAmountUseCase.depositAmount("2933",90);
        //THEN
        Assertions.assertEquals(new Account("2933",90+90),account);
    }

    @Test
    void shouldExceptionDepositAmountWithAccountNumberNotFound() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            depositAmountUseCase.depositAmount("12121",90);
        });
        //THEN
        Assertions.assertEquals("Error deposit amount with AccountNumber not found", thrown.getMessage());
    }

    @Test
    void shouldExceptionDepositAmountWithDatabaseError() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            depositAmountUseCase.depositAmount("00000",90);
        });
        //THEN
        Assertions.assertEquals("Error deposit account from Database", thrown.getMessage());
    }

    @Test
    void shouldDepositAmountWithDatabase() {
        //GIVE
        //WHEN
        createAccountUseCase.createAccount(new Account("2933",50));
        depositAmountUseCase.depositAmount("2933",10);
        var account = bankRepositoryPort.findAccountByAccountNumber("2933");
        var transactions = bankRepositoryPort.findAllTransactionByAccountNumber("2933");
        //THEN
        Assertions.assertEquals(new Account("2933",60),account.get());
        Assertions.assertEquals(2,transactions.size());
    }
}