package com.aleksejkam.simple.bank.repository;

import com.aleksejkam.simple.bank.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Transactions
 *
 * @author Aleksej Kaminskij
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

    public List<Transaction> findByAccountId(String accountId);
}