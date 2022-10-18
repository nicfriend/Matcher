package com.example.Matcher.exceptions;

public class AccountUsernameNotFoundException extends RuntimeException {

    public AccountUsernameNotFoundException(String username) {
        super("Could not find an account with the username: " + username);
    }
}
