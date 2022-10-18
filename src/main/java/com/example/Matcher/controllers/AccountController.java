package com.example.Matcher.controllers;

import com.example.Matcher.entities.Account;
import com.example.Matcher.services.AccountsService;
import com.example.Matcher.services.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountsService account;

    @Autowired
    PopulationService populate;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @PostMapping("/authenticate")
//    public String authenticate(@RequestBody Account user){
//        return account.sendToken(user);
//    }

    @PostMapping("/login")
    public String login(@RequestBody Account user) { return account.validLogin(user); }

    @GetMapping("/populate")
    public void addAccounts(){
        populate.populateAccounts();
    }

    @PostMapping("/create")
    public String createAccount(@RequestBody Account newAccount) {
        newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        account.saveAccount(newAccount);
        return account.sendToken(newAccount);
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

