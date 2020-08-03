package com.aleksejkam.simple.bank.model;

import lombok.Data;

import javax.persistence.*;

/**
 * User entity
 *
 * @author Aleksej Kaminskij
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;
}
