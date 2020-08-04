package com.aleksejkam.simple.bank.controller;

import com.aleksejkam.simple.bank.domain.TransactionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @WithMockUser(username = "user1", password = "pwd1", roles = "USER")
    public void shouldMakeTwoDeposits() throws Exception {
        // setup
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal(500));

        // execute
        MvcResult resultForDeposit1 = this.mockMvc.perform(post("/v1/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transactionRequest)))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult resultForDeposit2 = this.mockMvc.perform(post("/v1/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transactionRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // verify
        assertThat(resultForDeposit1.getResponse().getContentAsString()).contains("\"type\":\"DEPOSIT\",\"amount\":500");
        assertThat(resultForDeposit2.getResponse().getContentAsString()).contains("\"type\":\"DEPOSIT\",\"amount\":500");
    }

    @Test
    @Order(2)
    @WithMockUser(username = "user1", password = "pwd1", roles = "USER")
    public void shouldMakeWithdraw() throws Exception {
        // setup
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new BigDecimal(300));

        // execute
        MvcResult resultForWithdraw = this.mockMvc.perform(post("/v1/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transactionRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // verify
        assertThat(resultForWithdraw.getResponse().getContentAsString()).contains("\"type\":\"WITHDRAW\",\"amount\":300");
    }

    @Test
    @Order(3)
    @WithMockUser(username = "user1", password = "pwd1", roles = "USER")
    public void shouldCheckBalance() throws Exception {
        // execute
        MvcResult resultForAccount = this.mockMvc.perform(get("/v1/account"))
                .andExpect(status().isOk())
                .andReturn();

        // verify
        assertThat(resultForAccount.getResponse().getContentAsString()).contains("{\"id\":101,\"userId\":101,\"amount\":700.00");
    }

    @Test
    @Order(4)
    @WithMockUser(username = "user1", password = "pwd1", roles = "USER")
    public void shouldGetAllTransactions() throws Exception {
        // execute
        this.mockMvc.perform(get("/v1/account/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].type", org.hamcrest.Matchers.is("DEPOSIT")))
                .andExpect(jsonPath("$[2].type", org.hamcrest.Matchers.is("WITHDRAW")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}