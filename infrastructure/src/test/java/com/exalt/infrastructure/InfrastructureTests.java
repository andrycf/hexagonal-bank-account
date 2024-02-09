package com.exalt.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exalt.domain.model.Transaction;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;

@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
class InfrastructureTests {

    @Autowired
    private WebTestClient webTestClient;

    @ServiceConnection
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @Test
    void shouldCompleted() throws Exception {
        String accountNumber = "123456";
        create(accountNumber, 1000);
        getBalance(accountNumber, 1000);
        getTransactionHistory(accountNumber, 1);
        deposit(accountNumber, 1000, 2000);
        getTransactionHistory(accountNumber, 2);
        withdraw(accountNumber, -500, 1500);
        getTransactionHistory(accountNumber, 3);
        withdraw(accountNumber, -500, 1000);
        getBalance(accountNumber, 1000);
        getTransactionHistory(accountNumber, 4);
    }

    void getTransactionHistory(String accountNumber, int expectedCountTransaction) throws Exception {
        webTestClient.get()
                .uri("/transaction/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Transaction.class).hasSize(expectedCountTransaction);

    }

    void getBalance(String accountNumber, double expectedBalance) throws Exception {
        webTestClient.get()
                .uri("/account/{accountNumber}", accountNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").exists()
                .jsonPath("$.accountNumber").isEqualTo(accountNumber)
                .jsonPath("$.balance").isEqualTo(expectedBalance);
    }

    void create(String accountNumber, double balance) throws Exception {
        webTestClient.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new AccountDTO(accountNumber, balance))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").exists()
                .jsonPath("$.accountNumber").isEqualTo(accountNumber)
                .jsonPath("$.balance").isEqualTo(balance);
    }

    void deposit(String accountNumber, double amount, double expectedBalance) throws Exception {
        webTestClient.patch()
                .uri("/transaction/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new TransactionRequest(accountNumber, amount))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").exists()
                .jsonPath("$.accountNumber").isEqualTo(accountNumber)
                .jsonPath("$.balance").isEqualTo(expectedBalance);

    }

    void withdraw(String accountNumber, double amount, double expectedBalance) throws Exception {
        webTestClient.patch()
                .uri("/transaction/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new TransactionRequest(accountNumber, amount))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").exists()
                .jsonPath("$.accountNumber").isEqualTo(accountNumber)
                .jsonPath("$.balance").isEqualTo(expectedBalance);
    }

}
