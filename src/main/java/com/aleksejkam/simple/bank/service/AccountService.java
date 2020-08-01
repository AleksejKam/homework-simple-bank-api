package com.aleksejkam.simple.bank.service;

import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Account Service
 *
 * @author Aleksej Kaminskij
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> findAllAccount() {
        List<Account> accounts = new ArrayList<>();
        accountRepository.findAll().forEach(accounts::add);

        return accounts;
    }
}
