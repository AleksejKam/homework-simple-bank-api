package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldSaveAccount() {
        // setup
        Account account = Account.builder()
                .username("testUser")
                .lastLogin(LocalDateTime.now())
                .balance(new BigDecimal(0))
                .build();

        when(accountRepository.save(account))
                .thenAnswer(i -> i.getArguments()[0]);

        // execute
        Account savedAccount = accountService.saveAccount(account);

        // verify
        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getUsername()).isEqualTo("testUser");
        assertThat(savedAccount.getLastLogin()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedAccount.getBalance()).isEqualTo(new BigDecimal(0));
    }

    @Test
    void shouldFindAllAccount() {
    }
}