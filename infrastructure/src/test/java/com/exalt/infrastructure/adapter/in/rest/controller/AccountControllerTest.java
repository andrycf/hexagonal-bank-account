package com.exalt.infrastructure.adapter.in.rest.controller;

import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.out.postgres.entity.AccountEntity;
import com.exalt.infrastructure.adapter.out.postgres.repository.AccountRepository;
import com.exalt.infrastructure.utils.Utils;
import jakarta.inject.Inject;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Inject
    private AccountRepository accountRepository;
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(AccountController.class)
                .build();
    }
    @Test
    void getBalance() throws Exception {
        accountRepository.deleteAll();
        accountRepository.save(new AccountEntity("1234",1000));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new AccountDTO("12345", 2000))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(2000));
    }
}
