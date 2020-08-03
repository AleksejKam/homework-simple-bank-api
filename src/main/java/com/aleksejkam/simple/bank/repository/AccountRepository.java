package com.aleksejkam.simple.bank.repository;

import com.aleksejkam.simple.bank.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Accounts
 *
 * @author Aleksej Kaminskij
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountByUserId(Long userId);
}
