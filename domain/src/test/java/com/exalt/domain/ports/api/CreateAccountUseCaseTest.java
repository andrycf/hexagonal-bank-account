package com.exalt.domain.ports.api;

import com.exalt.domain.exceptions.BankException;
import com.exalt.domain.model.Account;
import com.exalt.domain.ports.spi.BankRepositoryPort;
import com.exalt.domain.ports.spi.stubs.BankRepositoryPortStub;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class CreateAccountUseCaseTest {

    private CreateAccountUseCase createAccountUseCase;
    private BankRepositoryPort bankRepositoryPort;

    @BeforeEach
    void setUp(){
        bankRepositoryPort = new BankRepositoryPortStub();
        createAccountUseCase = new CreateAccountUseCaseImpl(bankRepositoryPort);
    }
    @Test
    void shouldCreateAccount() {
        //GIVEN
        var account = new Account("12345",100);
        //WHEN
        var newAccount = createAccountUseCase.createAccount(account);
        //THEN
        Assertions.assertNotNull(newAccount);
        Assertions.assertSame(account,newAccount);
    }

    @Test
    void shouldExceptionCreateAccountWithAccountNumberNull() {
        //GIVEN
        var account = new Account(null,100);
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(account);
        });
        //THEN
        Assertions.assertEquals("Error created account with AccountNumber Null or Empty", thrown.getMessage());
    }

    @Test
    void shouldExceptionCreateAccountWithAccountNumberEmpty() {
        //GIVEN
        var account = new Account(" ",100);
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(account);
        });
        //THEN
        Assertions.assertEquals("Error created account with AccountNumber Null or Empty", thrown.getMessage());
    }

    @Test
    void shouldCreateAccountWithDatabaseBalance100() {
        //GIVEN
        var account = new Account("12345",100);
        //WHEN
        createAccountUseCase.createAccount(account);
        Optional<Account> newAccount = bankRepositoryPort.findAccountByAccountNumber(account.accountNumber());
        //THEN
        Assertions.assertNotNull(newAccount);
        Assertions.assertEquals(account,newAccount.get());
    }

    @Test
    void shouldCreateAccountWithDatabaseBalance200() {
        //GIVEN
        var account = new Account("12345",200);
        //WHEN
        createAccountUseCase.createAccount(account);
        Optional<Account> newAccount = bankRepositoryPort.findAccountByAccountNumber(account.accountNumber());
        //THEN
        Assertions.assertNotNull(newAccount);
        Assertions.assertEquals(account,newAccount.get());
    }

    @Test
    void shouldExceptionCreateAccountWithAccountExistDatabase() {
        //GIVEN
        var account = new Account("12345",89);
        //WHEN
        createAccountUseCase.createAccount(account);
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(account);
        });
        //THEN
        Assertions.assertEquals("Error created account with AccountNumber already exist", thrown.getMessage());
    }

    @Test
    void shouldExceptionCreateAccountWithFindAccountDatabaseError() {
        //GIVEN
        var account = new Account("00000",89);
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(account);
        });
        //THEN
        Assertions.assertEquals("Error created account from Database", thrown.getMessage());
    }

    @Test
    void shouldExceptionCreateAccountWithSaveAccountDatabaseError() {
        //GIVEN
        var account = new Account("1111",89);
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(account);
        });
        //THEN
        Assertions.assertEquals("Error created account from Database", thrown.getMessage());
    }

    @Test
    void shouldExceptionCreateAccountNull() {
        //WHEN
        var thrown = Assertions.assertThrows(BankException.class,() -> {
            createAccountUseCase.createAccount(null);
        });
        //THEN
        Assertions.assertEquals("Error created account null", thrown.getMessage());
    }
}