package com.exalt.infrastructure.adapter.in.rest.controller;

import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;
import com.exalt.infrastructure.adapter.out.postgres.entity.TransactionEntity;
import com.exalt.infrastructure.adapter.out.postgres.repository.AccountRepository;
import com.exalt.infrastructure.adapter.out.postgres.repository.TransactionRepository;
import com.exalt.infrastructure.utils.Utils;
import jakarta.inject.Inject;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Inject
    private TransactionRepository transactionRepository;
    @Inject
    private AccountRepository accountRepository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(TransactionController.class)
                .build();
    }

    @Test
    void getTransactionHistory() throws Exception {
        transactionRepository.deleteAll();
        transactionRepository.save(new TransactionEntity(
                "12345",
                LocalDateTime.now(),
                100
        ));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/12345")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(100));

    }

    @Test
    void deposit() throws Exception {
        accountRepository.deleteAll();
        accountRepository.save(new AccountEntity("12345",1000));
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transaction/deposit")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new TransactionRequest("12345", 2000))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(3000));

    }

    @Test
    void withdraw() throws Exception {
        accountRepository.deleteAll();
        accountRepository.save(new AccountEntity("12345",1000));
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transaction/withdraw")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new TransactionRequest("12345", -500))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(500));

    }
}