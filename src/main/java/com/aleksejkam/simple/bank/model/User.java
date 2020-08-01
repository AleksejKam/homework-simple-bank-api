package com.aleksejkam.simple.bank.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User entity
 *
 * @author Aleksej Kaminskij
 */
@Entity
@Data
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
}
