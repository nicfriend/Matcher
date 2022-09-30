package com.example.Matcher.interfaces;

import com.example.Matcher.Aggregate;
import com.example.Matcher.entities.Order;
import com.example.Matcher.entities.Trade;

import java.util.ArrayList;

public interface MatcherInterface {

    void addOrder(Order newOrder);

    void lowToHigh(ArrayList<Order> toSell);

    void highToLow(ArrayList<Order> toBuy);

    void sort(ArrayList<Order> relevantList);

    void rearrange(ArrayList<Order> relevantList);

    void trade(Order newOrder);

    ArrayList<ArrayList<Aggregate>> aggregate(int aggNum);

    void privateOrderBook(String accountName);

    /**
     *
     * @param sellAcc
     * @param buyAcc
     * @param quantity
     * @param price
     * @param counter
     *
     * @return
     */
    Trade tradeHistory(String sellAcc, String buyAcc, int quantity, int price, int counter);
}
