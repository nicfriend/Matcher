package com.example.Matcher.controllers;
import com.example.Matcher.*;
import com.example.Matcher.entities.Order;
import com.example.Matcher.entities.Trade;
import com.example.Matcher.entities.Account;
import com.example.Matcher.services.MatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
public class MatcherController {
    @Autowired
    private MatcherService matcher;


    @GetMapping("/")
    public String intro(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/tradedata")
    public ArrayList<Trade> tradeData() {
        return matcher.sendTradeData();
    }

    @GetMapping("/aggregate")
    public ArrayList<ArrayList<Aggregate>> aggregateList(@Valid @RequestParam int agg) {
        return matcher.sendAggregatedOrderBooks(agg);
    }

    @GetMapping("/privateorders")
    public ArrayList<Order> privateOrder(Authentication Authorization) {
        String account = Authorization.getName();
        return matcher.sendPrivateOrderBook(account);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/neworder")
    public ArrayList<Object> newOrder(@Valid @RequestBody Order order, @RequestParam int agg, Authentication Authorization) {
        if (Authorization.getName().equals(order.getAccount())){
            return matcher.sendNewOrder(order, agg, Authorization);
        }
        return null;
    }
}

