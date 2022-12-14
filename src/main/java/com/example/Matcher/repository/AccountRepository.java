package com.example.Matcher.repository;

import com.example.Matcher.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    boolean existsByUsername(String username);
    Account findByUsername(String username);
}
