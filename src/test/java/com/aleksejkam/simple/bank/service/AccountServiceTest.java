package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.model.User;
import com.aleksejkam.simple.bank.repository.AccountRepository;
import com.aleksejkam.simple.bank.repository.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldFindAccountByUsername() throws NotFoundException {
        // setup
        User user = new User();
        user.setId(101L);

        Account account = Account.builder()
                .id(10L)
                .userId(101L)
                .amount(new BigDecimal(125))
                .lastUpdate(LocalDateTime.now())
                .build();

        when(userRepository.findByUsername("userName"))
                .thenReturn(user);

        when(accountRepository.findAccountByUserId(101L))
                .thenReturn(account);

        // execute
        Account foundAccount = accountService.findAccountByUsername("userName");

        // verify
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getId()).isEqualTo(10L);
        assertThat(foundAccount.getUserId()).isEqualTo(101L);
        assertThat(foundAccount.getAmount()).isEqualTo(new BigDecimal(125));
        assertThat(foundAccount.getLastUpdate()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}