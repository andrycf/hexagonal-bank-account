package com.exalt.domain.ports.api;

import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.domain.ports.spi.stubs.BankRepositoryPortStub;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;
import com.exalt.domain.usecases.WithdrawAmountUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WithdrawAmountUseCaseTest {

    private WithdrawAmountUseCase withDrawAmountUseCase;
    private CreateAccountUseCase createAccountUseCase;
    private BankRepositoryPort bankRepositoryPort;

    @BeforeEach
    void setUp(){
        bankRepositoryPort = new BankRepositoryPortStub();
        withDrawAmountUseCase = new WithdrawAmountUseCaseImpl(bankRepositoryPort);
        createAccountUseCase = new CreateAccountUseCaseImpl(bankRepositoryPort);
    }
    @Test
    void shouldExceptionWithdrawAmountWithAccountNumberNull() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount(null,90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount with AccountNumber Null or Empty", thrown.getMessage());
    }
    @Test
    void shouldExceptionWithdrawAmountWithAccountNumberEmpty() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("   ",90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount with AccountNumber Null or Empty", thrown.getMessage());
    }
    @Test
    void shouldExceptionWithdrawAmountPositive() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("12121",90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount greater than or equal to 0", thrown.getMessage());
    }

    @Test
    void shouldWithDrawAmount() {
        //GIVE
        //WHEN
        bankRepositoryPort.saveAccount(new Account("2933",90));
        var account = withDrawAmountUseCase.withdrawAmount("2933",-80);
        //THEN
        Assertions.assertEquals(new Account("2933",10),account);
    }

    @Test
    void shouldExceptionWithdrawAmountWithAccountNumberNotFound() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("12121",-90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount with AccountNumber not found", thrown.getMessage());
    }

    @Test
    void shouldExceptionWithdrawAmountWithDatabaseError() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("00000",-90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount from Database", thrown.getMessage());
    }

    @Test
    void shouldWithdrawAmountWithDatabase() {
        //GIVE
        //WHEN
        createAccountUseCase.createAccount(new Account("2933",50));
        withDrawAmountUseCase.withdrawAmount("2933",-10);
        var account = bankRepositoryPort.findAccountByAccountNumber("2933");
        var transactions = bankRepositoryPort.findAllTransactionByAccountNumber("2933");
        //THEN
        Assertions.assertEquals(new Account("2933",40),account.get());
        Assertions.assertEquals(2,transactions.size());
    }

    @Test
    void shouldExceptionWithDrawAmountWithDatabaseError() {
        //GIVE
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("00000",-90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw amount from Database", thrown.getMessage());
    }

    @Test
    void shouldExceptionWithDrawAmountWithAmountGreaterThanBalance() {
        //GIVE
        //WHEN
        createAccountUseCase.createAccount(new Account("2933",50));
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            withDrawAmountUseCase.withdrawAmount("2933",-90);
        });
        //THEN
        Assertions.assertEquals("Error withdraw account with Insufficient funds", thrown.getMessage());
    }
}