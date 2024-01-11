package com.exalt.domain.ports.api;

import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.domain.ports.spi.stubs.BankRepositoryPortStub;
import com.exalt.domain.usecases.CheckTransactionUseCaseImpl;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;
import com.exalt.domain.usecases.DepositAmountUseCaseImpl;
import com.exalt.domain.usecases.WithdrawAmountUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckTransactionUseCaseTest {
    private CheckTransactionUseCase checkTransactionUseCase;
    private CreateAccountUseCase createAccountUseCase;
    private DepositAmountUseCase depositAmountUseCase;
    private WithdrawAmountUseCase withDrawAmountUseCase;
    private BankRepositoryPort bankRepositoryPort;

    @BeforeEach
    void setUp(){
        bankRepositoryPort = new BankRepositoryPortStub();
        createAccountUseCase = new CreateAccountUseCaseImpl(bankRepositoryPort);
        depositAmountUseCase = new DepositAmountUseCaseImpl(bankRepositoryPort);
        withDrawAmountUseCase = new WithdrawAmountUseCaseImpl(bankRepositoryPort);
        checkTransactionUseCase = new CheckTransactionUseCaseImpl(bankRepositoryPort);
    }
    @Test
    void shouldExceptionCheckTransactionHistoryNull() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkTransactionUseCase.checkTransactionHistory(null);
        });
        //THEN
        Assertions.assertEquals("Error check transaction with AccountNumber Null or Empty", thrown.getMessage());

    }

    @Test
    void shouldExceptionCheckTransactionHistoryEmpty() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkTransactionUseCase.checkTransactionHistory("  ");
        });
        //THEN
        Assertions.assertEquals("Error check transaction with AccountNumber Null or Empty", thrown.getMessage());
    }

    @Test
    void shouldCheckTransactionHistory() {
        //GIVEN
        //WHEN
        createAccountUseCase.createAccount(new Account("1622",17));
        depositAmountUseCase.depositAmount("1622",3);
        withDrawAmountUseCase.withdrawAmount("1622",-5);
        var transactions = checkTransactionUseCase.checkTransactionHistory("1622");
        //THEN
        Assertions.assertEquals(3,transactions.size());
    }

    @Test
    void shouldExceptionCheckTransactionHistoryWithDatabaseErrorFind() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkTransactionUseCase.checkTransactionHistory("00000");
        });
        //THEN
        Assertions.assertEquals("Error check transaction from Database Error", thrown.getMessage());

    }

    @Test
    void shouldCheckTransactionHistoryWithDatabaseError() {
        //GIVEN
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            checkTransactionUseCase.checkTransactionHistory("00000");
        });
        //THEN
        Assertions.assertEquals("Error check transaction from Database Error", thrown.getMessage());

    }

    @Test
    void shouldCheckTransactionHistoryWithDatabaseEmpty() {
        //GIVEN
        //WHEN
        var transactions = checkTransactionUseCase.checkTransactionHistory("90877");
        //THEN
        Assertions.assertEquals(0, transactions.size());

    }
}