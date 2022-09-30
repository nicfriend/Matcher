package com.example.Matcher.repository;

import com.example.Matcher.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Account, String> {
}
