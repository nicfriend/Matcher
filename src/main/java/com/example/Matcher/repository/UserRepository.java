package com.example.Matcher.repository;

import com.example.Matcher.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Accounts, Integer> {

}
