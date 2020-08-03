package com.aleksejkam.simple.bank.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * Transaction Request
 *
 * @author Aleksej Kaminskij
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionRequest {

    private BigDecimal amount;
}
