package com.aleksejkam.simple.bank.controller;

import com.aleksejkam.simple.bank.model.Account;
import com.aleksejkam.simple.bank.model.Transaction;
import com.aleksejkam.simple.bank.domain.TransactionRequest;
import com.aleksejkam.simple.bank.service.AccountService;
import com.aleksejkam.simple.bank.service.TransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Account> getAccount() throws NotFoundException {
        Account account = accountService.findAccountByUsername(getAuthenticatedUserName().get());

        return new ResponseEntity<>(account, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> makeDeposit(@RequestBody TransactionRequest transactionRequest) throws NotFoundException {
        Account account = accountService.findAccountByUsername(getAuthenticatedUserName().get());

        accountService.makeDeposit(account, transactionRequest);

        return ResponseEntity.ok("Deposit is done");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> makeWithdraw(@RequestBody TransactionRequest transactionRequest) throws NotFoundException {
        Account account = accountService.findAccountByUsername(getAuthenticatedUserName().get());

        accountService.makeWithdraw(account, transactionRequest);

        return ResponseEntity.ok("withdraw is done");
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() throws NotFoundException {
        Account account = accountService.findAccountByUsername(getAuthenticatedUserName().get());

        return ResponseEntity.ok(transactionService.getTransactionsByAccountId(account.getId()));
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionId) throws NotFoundException {
        Transaction transaction = transactionService.getTransactionById(transactionId);

        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transaction);
    }

    private Optional<String> getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return Optional.of(authentication.getName());
        } else {
            return Optional.empty();
        }
    }
}
