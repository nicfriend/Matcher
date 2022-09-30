package com.example.Matcher.controllers;

import com.example.Matcher.entities.Account;
import com.example.Matcher.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountsService account;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody Account user){
        return account.sendToken(user);
    }

    @PostMapping()
    public void createAccount(@RequestBody Account newAccount) {
        account.saveAccount(newAccount);
    }

    @GetMapping()
    public Account readAccount(@RequestParam Integer id) {
        return account.getAccountById(id);
    }

    @PutMapping()
    public void updateAccount(@RequestBody Account newAccount) {
        account.updateAccount(newAccount);
    }

    @DeleteMapping()
    public Account deleteAccount(@RequestParam Integer id) {
        return account.deleteAccount(id);
    }

}

