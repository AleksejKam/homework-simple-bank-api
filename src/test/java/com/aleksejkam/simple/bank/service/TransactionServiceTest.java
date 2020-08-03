package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.model.Transaction;
import com.aleksejkam.simple.bank.repository.TransactionRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldGetTransactionById() throws NotFoundException {
        // setup
        Transaction transaction = Transaction.builder()
                .id(3L)
                .accountId(101L)
                .type(Transaction.TransactionType.WITHDRAW)
                .amount(new BigDecimal(500))
                .dateTime(LocalDateTime.now())
                .build();

        when(transactionRepository.findById(3L))
                .thenReturn(Optional.of(transaction));

        // execute
        Transaction foundTransaction = transactionService.getTransactionById(3L);

        // verify
        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction.getId()).isEqualTo(3L);
        assertThat(foundTransaction.getAccountId()).isEqualTo(101L);
        assertThat(foundTransaction.getType()).isEqualTo(Transaction.TransactionType.WITHDRAW);
        assertThat(foundTransaction.getAmount()).isEqualTo(new BigDecimal(500));
        assertThat(foundTransaction.getDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void shouldThrowNotFoundExceptionOnGetTransactionById() {
        // setup
        when(transactionRepository.findById(3L))
                .thenReturn(Optional.empty());

        // execute
        Exception thrownException = assertThrows(NotFoundException.class, () -> {
            transactionService.getTransactionById(3L);
        });

        // verify
        assertThat(thrownException.getMessage()).isEqualTo("Transaction with id '3' is not found");
    }
}
