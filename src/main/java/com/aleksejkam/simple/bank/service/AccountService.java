package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.domain.TransactionRequest;
import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.model.Transaction;
import com.aleksejkam.simple.bank.model.User;
import com.aleksejkam.simple.bank.repository.AccountRepository;
import com.aleksejkam.simple.bank.repository.TransactionRepository;
import com.aleksejkam.simple.bank.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Account Service
 *
 * @author Aleksej Kaminskij
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Make deposit
     *
     * @param account
     * @param transactionRequest amount container
     * @return executed transaction
     */
    @Transactional
    public Transaction makeDeposit(Account account, TransactionRequest transactionRequest) {

        BigDecimal deposit = account.getAmount().add(transactionRequest.getAmount());

        account.setAmount(deposit);
        account.setLastUpdate(LocalDateTime.now());

        accountRepository.save(account);

        Transaction transaction = new Transaction().builder()
                .accountId(account.getId())
                .type(Transaction.TransactionType.DEPOSIT)
                .amount(transactionRequest.getAmount())
                .dateTime(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    /**
     * Make withdraw
     *
     * @param account
     * @param transactionRequest amount container
     * @return executed transaction
     * @throws NotFoundException
     */
    @Transactional
    public Transaction makeWithdraw(Account account, TransactionRequest transactionRequest) throws NotFoundException {

        if (account.getAmount().compareTo(transactionRequest.getAmount()) < 0) {
            throw new NotFoundException("Not enough funds for Withdraw");
        }

        BigDecimal deposit = account.getAmount().subtract(transactionRequest.getAmount());

        account.setAmount(deposit);
        account.setLastUpdate(LocalDateTime.now());

        accountRepository.save(account);

        Transaction transaction = new Transaction().builder()
                .accountId(account.getId())
                .type(Transaction.TransactionType.WITHDRAW)
                .amount(transactionRequest.getAmount())
                .dateTime(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    /**
     * Find Account by userName
     *
     * @param userName
     * @return founded Account
     * @throws NotFoundException
     */
    public Account findAccountByUsername(String userName) throws NotFoundException {
        User user = userRepository.findByUsername(userName);
        Account account = accountRepository.findAccountByUserId(user.getId());

        return account;
    }
}
