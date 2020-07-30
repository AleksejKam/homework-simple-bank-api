package com.aleksejkam.simple.bank.repository;

import com.aleksejkam.simple.bank.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveAccount() {
        // setup
        when(accountRepository.save(Mockito.any(Account.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Account account = Account.builder()
                .username("testUser")
                .lastLogin(LocalDateTime.now())
                .balance(new BigDecimal(0))
                .build();

        // execute
        Account savedAccount = accountRepository.save(account);

        // verify
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getUsername()).isEqualTo("testUser");
        assertThat(savedAccount.getLastLogin()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedAccount.getBalance()).isEqualTo(new BigDecimal(0));
    }
}