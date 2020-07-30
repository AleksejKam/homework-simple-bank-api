package com.aleksejkam.simple.bank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Account entity
 *
 * @author Aleksej Kaminskij
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private UUID id;
    private String username;
    private String password;
    private LocalDateTime lastLogin;
    private BigDecimal balance;
}
