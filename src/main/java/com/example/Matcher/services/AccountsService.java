package com.example.Matcher.services;

import com.example.Matcher.entities.Account;
import com.example.Matcher.exceptions.AccountIdNotFoundException;
import com.example.Matcher.exceptions.AccountUsernameNotFoundException;
import com.example.Matcher.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {
    @Autowired
    AccountRepository accountRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountIdNotFoundException(id));
    }

    public String validLogin(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())){
            return sendToken(account);
        }
        throw new AccountUsernameNotFoundException(account.getUsername());
    }

    public void updateAccount(Account newAccount) {
        Account acc = accountRepository.findById(newAccount.getAccountId())
                .orElseThrow(() -> new AccountIdNotFoundException(newAccount.getAccountId()));
        acc.setUsername(newAccount.getUsername());
        acc.setPassword(newAccount.setPassword(passwordEncoder().encode(newAccount.getPassword())));
        accountRepository.save(acc);
    }

    public Account deleteAccount(Integer id){
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new AccountIdNotFoundException(id));
        accountRepository.deleteById(id);
        return null;
    }

    public String sendToken(Account user) { return user.generateToken(user.getUsername()); }

}
