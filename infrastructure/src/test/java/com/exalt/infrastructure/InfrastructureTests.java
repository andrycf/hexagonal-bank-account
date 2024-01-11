package com.exalt.infrastructure;

import com.exalt.infrastructure.adapter.in.rest.controller.AccountController;
import com.exalt.infrastructure.adapter.in.rest.controller.TransactionController;
import com.exalt.infrastructure.adapter.in.rest.dto.AccountDTO;
import com.exalt.infrastructure.adapter.in.rest.dto.TransactionRequest;
import com.exalt.infrastructure.utils.Utils;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class InfrastructureTests {

    @Autowired
    private MockMvc mockMvc;
    @ServiceConnection
    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(AccountController.class, TransactionController.class)
                .build();
    }

    @Test
    void shouldCompleted() throws Exception {
        String accountNumber = "123456";
        create(accountNumber,1000);
        getBalance(accountNumber,1000);
        getTransactionHistory(accountNumber,1);
        deposit(accountNumber,1000,2000);
        getTransactionHistory(accountNumber,2);
        withdraw(accountNumber,-500,1500);
        getTransactionHistory(accountNumber,3);
        withdraw(accountNumber,-500,1000);
        getBalance(accountNumber,1000);
        getTransactionHistory(accountNumber,4);
    }

    void getTransactionHistory(String accountNumber,int expectedCountTransaction) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/"+accountNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").value(hasSize(expectedCountTransaction)));

    }

    void getBalance(String accountNumber,double expectedBalance) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account/"+accountNumber)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(expectedBalance));
    }
    void create(String accountNumber,double balance) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new AccountDTO(accountNumber, balance))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(balance));
    }

    void deposit(String accountNumber,double amount,double expectedBalance) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transaction/deposit")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new TransactionRequest(accountNumber, amount))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(expectedBalance));

    }
    void withdraw(String accountNumber,double amount,double expectedBalance) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transaction/withdraw")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(Utils.asJsonString(new TransactionRequest(accountNumber, amount))))
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(expectedBalance));

    }

}
