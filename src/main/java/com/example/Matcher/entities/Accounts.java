package com.example.Matcher.entities;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.Data;

import javax.persistence.*;
import java.util.function.Function;

@Table
@Entity
public class Accounts {


    @Id
    @Column(name="accountId")
    private int accountId;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    byte[] key = TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");

    public String generateToken(String username) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        JwtBuilder jwt = Jwts.builder()
                .setSubject(username)
                .claim("username", getUsername())
                .signWith(signatureAlgorithm, key);
        return jwt.compact();
    }
}
