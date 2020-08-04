package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.model.Transaction;
import com.aleksejkam.simple.bank.repository.TransactionRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Transaction Service
 *
 * @author Aleksej Kaminskij
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Get Transaction by transactionId
     *
     * @param transactionId
     * @return founded Transaction
     * @throws NotFoundException
     */
    public Transaction getTransactionById(Long transactionId) throws NotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new NotFoundException(String.format("Transaction with id '%d' is not found", transactionId));
        }
    }

    /**
     * Get All Transactions by accountId
     *
     * @param accountId
     * @return the List of Transactions
     */
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAllByAccountId(accountId);
    }
}
