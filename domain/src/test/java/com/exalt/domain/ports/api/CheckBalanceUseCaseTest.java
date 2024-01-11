package com.exalt.domain.ports.api;

import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.domain.ports.spi.stubs.BankRepositoryPortStub;
import com.exalt.domain.usecases.CheckBalanceUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckBalanceUseCaseTest {
    private CheckBalanceUseCase checkBalanceUseCase;
    private BankRepositoryPort bankRepositoryPort;

    @BeforeEach
    void setUp(){
        bankRepositoryPort = new BankRepositoryPortStub();
        checkBalanceUseCase = new CheckBalanceUseCaseImpl(bankRepositoryPort);
    }
    @Test
    void shouldExceptionCheckBalanceWithAccounNumberNull() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkBalanceUseCase.checkBalance(null);
        });
        //THEN
        Assertions.assertEquals("Error check balance with AccountNumber Null or Empty", thrown.getMessage());
    }

    @Test
    void shouldExceptionCheckBalanceWithAccounNumberEmpty() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkBalanceUseCase.checkBalance("  ");
        });
        //THEN
        Assertions.assertEquals("Error check balance with AccountNumber Null or Empty", thrown.getMessage());
    }

    @Test
    void shouldExceptionCheckBalanceWithAcccountNumberNotFound() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkBalanceUseCase.checkBalance("6788");
        });
        //THEN
        Assertions.assertEquals("Error check balance with AccountNumber not found", thrown.getMessage());
    }

    @Test
    void shouldExceptionCheckBalanceWithDatabaseError() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkBalanceUseCase.checkBalance("00000");
        });
        //THEN
        Assertions.assertEquals("Error check balance from Database", thrown.getMessage());
    }

    @Test
    void shouldCheckBalance100() {
        //GIVEN
        var account = new Account("6788",100);
        //WHEN
        bankRepositoryPort.saveAccount(account);
        var balance = checkBalanceUseCase.checkBalance("6788");
        //THEN
        Assertions.assertEquals(100,balance);
    }

    @Test
    void shouldCheckBalance200() {
        //GIVEN
        var account = new Account("6788",200);
        //WHEN
        bankRepositoryPort.saveAccount(account);
        var balance = checkBalanceUseCase.checkBalance("6788");
        //THEN
        Assertions.assertEquals(200,balance);
    }
}