package com.example.Matcher.exceptions;

public class AccountIdNotFoundException extends RuntimeException {

    public AccountIdNotFoundException(Integer id){
        super("Could not find an account with id: " + id);
    }
}
