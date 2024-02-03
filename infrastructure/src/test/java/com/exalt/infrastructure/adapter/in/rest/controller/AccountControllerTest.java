package com.exalt.infrastructure.adapter.in.rest.controller;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;
import com.exalt.infrastructure.adapter.out.postgres.repository.AccountRepository;
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

@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
class AccountControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Inject
    private AccountRepository accountRepository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeEach
    void setUp(){
        accountRepository.deleteAll();
    }

    @Test
    void getBalance() throws Exception {
        accountRepository.save(new AccountEntity("1234",1000));

        webTestClient.get().uri("/account/1234")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.*").exists()
        .jsonPath("$.accountNumber").isEqualTo("1234")
        .jsonPath("$.balance").isEqualTo(1000);
    }

    @Test
    void create() throws Exception {
        webTestClient.post().uri("/account")
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(new AccountDTO("12345", 2000))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$.*").exists()
        .jsonPath("$.accountNumber").isEqualTo("12345")
        .jsonPath("$.balance").isEqualTo(2000);
    }
}
