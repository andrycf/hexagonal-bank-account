package com.exalt.infrastructure.adapter.in.rest.controller;

import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;
import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;
import com.exalt.infrastructure.adapter.out.postgres.repository.AccountRepository;
import com.exalt.infrastructure.adapter.out.postgres.repository.TransactionRepository;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Inject
    private TransactionRepository transactionRepository;
    @Inject
    private AccountRepository accountRepository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeEach
    void setUp(){
        transactionRepository.deleteAll();
    }

    @Test
    void getTransactionHistory() throws Exception {
        transactionRepository.save(new TransactionEntity(
                "12345",
                LocalDateTime.now(),
                100
        ));

        webTestClient.get().uri("/transaction/12345")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.*").exists()
        .jsonPath("$[0].amount").isEqualTo(100)
        .returnResult();

    }

    @Test
    void deposit() throws Exception {
        accountRepository.save(new AccountEntity("12345",1000));

        webTestClient.patch().uri("/transaction/deposit")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(new TransactionRequest("12345", 2000))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.*").exists()
        .jsonPath("$.accountNumber").isEqualTo("12345")
        .jsonPath("$.balance").isEqualTo(3000);
    }

    @Test
    void withdraw() throws Exception {
        accountRepository.save(new AccountEntity("12345",1000));

        webTestClient.patch().uri("/transaction/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(new TransactionRequest("12345", -500))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").exists()
                .jsonPath("$.accountNumber").isEqualTo("12345")
                .jsonPath("$.balance").isEqualTo(500);

    }
}