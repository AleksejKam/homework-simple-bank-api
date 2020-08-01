package com.aleksejkam.simple.bank.controller;

import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Account Controller
 *
 * @author Aleksej Kaminskij
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public Account createAccount(@RequestBody Account account) {
        account.setUsername(getUserName().get());

        return accountService.saveAccount(account);
    }

    @GetMapping("/get-all")
    public List<Account> getAllAccount() {
        return accountService.findAllAccount();
    }

    private Optional<String> getUserName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return Optional.of(authentication.getName());
        } else {
            return Optional.empty();
        }

    }
}
