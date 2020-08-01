package com.aleksejkam.simple.bank.repository;

import com.aleksejkam.simple.bank.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Users
 *
 * @author Aleksej Kaminskij
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
