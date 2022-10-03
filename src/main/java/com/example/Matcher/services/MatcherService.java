package com.example.Matcher.services;

import com.example.Matcher.Aggregate;
import com.example.Matcher.Matcher;
import com.example.Matcher.entities.Order;
import com.example.Matcher.entities.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MatcherService {

    @Autowired
    private Matcher matcher;

    public ArrayList<Object> sendNewOrder(Order order, int agg, Authentication auth) {
        matcher.trade(order);
        matcher.aggregate(agg);
        ArrayList<Object> returnList = new ArrayList<Object>();
        returnList.add(matcher.getAggregatedBuyList());
        returnList.add(matcher.getAggregatedSellList());
        returnList.add(matcher.getTradeList());
        return returnList;
    }

    public ArrayList<Order> sendPrivateOrderBook(String account) {
        matcher.privateOrderBook(account);
        return matcher.getOneAccount();
    }

    public ArrayList<ArrayList<Aggregate>> sendAggregatedOrderBooks(int aggNum) {
        return matcher.aggregate(aggNum);
    }

    public ArrayList<Trade> sendTradeData() {
        return matcher.getTradeList();
    }
}
