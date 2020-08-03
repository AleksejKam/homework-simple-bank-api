package com.aleksejkam.simple.bank.repository;

import com.aleksejkam.simple.bank.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void shouldSaveTransaction() {
        // setup
        when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Transaction transaction = Transaction.builder()
                .accountId(101L)
                .type(Transaction.TransactionType.DEPOSIT)
                .amount(new BigDecimal(125))
                .build();

        // execute
        Transaction savedTransaction = transactionRepository.save(transaction);

        // verify
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getAccountId()).isEqualTo(101L);
        assertThat(savedTransaction.getType()).isEqualTo(Transaction.TransactionType.DEPOSIT);
        assertThat(savedTransaction.getAmount()).isEqualTo(new BigDecimal(125));
    }
}