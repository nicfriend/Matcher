package com.example.Matcher.services;

import com.example.Matcher.entities.Account;
import com.example.Matcher.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PopulationService {

    @Autowired
    AccountRepository accountRepository;

     @Autowired
     PasswordEncoder passwordEncoder;

    public void populateAccounts() {
        Account order1 = new Account(100, "Nic", passwordEncoder.encode("myPassword"));
        Account order2 = new Account(100, "Louis", passwordEncoder.encode("LouisPassword"));
        Account order3 = new Account(100, "Ibrahim", passwordEncoder.encode("IbrahimPassword"));
        Account order4 = new Account(100, "Jack", passwordEncoder.encode("JackPassword"));
        accountRepository.save(order1);
        accountRepository.save(order2);
        accountRepository.save(order3);
        accountRepository.save(order4);
    }
}
