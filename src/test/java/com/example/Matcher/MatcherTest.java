package com.example.Matcher;

import com.example.Matcher.entities.Order;
import com.example.Matcher.enums.Action;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MatcherTest {
private Matcher matcher;
    @BeforeEach
    void setUp() {
        matcher = new Matcher();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void addOrders(){
        Order order1 = new Order("Nic", 15, 12, Action.SELL, 1000);
        Order order2 = new Order("Nic", 15, 12, Action.BUY, 1000);
        matcher.addOrder(order1);
        matcher.addOrder(order1);
        matcher.addOrder(order2);
        assertEquals (matcher.getToSell().size(), 2);
        assertEquals (matcher.getToBuy().size(), 1);
    }

    @Test
    void aggregate(){
        Order order1 = new Order("Nic", 15, 12, Action.SELL, 1000);
        Order order2 = new Order("Nicholas", 14, 14, Action.SELL, 1000);
        Order order3 = new Order("George", 21, 30, Action.SELL, 1000);
        matcher.trade(order1);
        matcher.aggregate(10);
        matcher.trade(order2);
        matcher.aggregate(10);
        matcher.trade(order3);
        matcher.aggregate(10);
        assertEquals (matcher.getAggregatedSellList().size(), 2);
        assertEquals(matcher.getAggregatedSellList().get(0).quantity, 14);
        assertEquals(matcher.getAggregatedSellList().get(1).quantity, 42);
        assertEquals(matcher.getToSell().size(), 3);
    }

    @Test
    void sortBuy(){
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 14, 14, Action.BUY, 1000);
        Order order3 = new Order("George", 15, 30, Action.BUY, 1000);
        matcher.addOrder(order2);
        matcher.addOrder(order1);
        matcher.addOrder(order3);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 12);
        assertEquals(matcher.getToBuy().get(1).getQuantity(), 30);
        assertEquals(matcher.getToBuy().get(1).getCounter(), 2);
    }

    @Test
    void sortSell(){
        Order order1 = new Order("Nic", 15, 12, Action.SELL, 1000);
        Order order2 = new Order("Nicholas", 14, 14, Action.SELL, 1000);
        Order order3 = new Order("George", 15, 30, Action.SELL, 1000);
        matcher.addOrder(order1);
        matcher.addOrder(order2);
        matcher.addOrder(order3);
        assertEquals(matcher.getToSell().get(0).getQuantity(), 14);
        assertEquals(matcher.getToSell().get(1).getQuantity(), 12);
        assertEquals(matcher.getToSell().get(2).getQuantity(), 30);
    }

    @Test
    void rearrange() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 14, 14, Action.BUY, 1000);
        Order order3 = new Order("Louis", 15, 15, Action.BUY, 1000);
        Order order4 = new Order("George", 16, 30, Action.BUY, 1000);
        matcher.addOrder(order1);
        matcher.addOrder(order2);
        matcher.addOrder(order3);
        matcher.addOrder(order4);
        assertEquals(matcher.getToBuy().get(1).getQuantity(), 12);
    }

    @Test
    void duplicateTests() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 14, 14, Action.BUY, 1000);
        Order order3 = new Order("Nic", 15, 12, Action.BUY, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 12);
    }

    @Test
    void fullTradePartialTrade() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 16, 8, Action.BUY, 1000);
        Order order3 = new Order("George", 15, 15, Action.SELL, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        assertEquals(matcher.getToSell().size(), 0);
        assertEquals(matcher.getToBuy().size(), 1);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 5);
    }

    @Test
    void threeFullTrade() {
        Order order1 = new Order("Nic", 15, 6, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 16, 8, Action.BUY, 1000);
        Order order3 = new Order("Louis", 17, 4, Action.BUY, 1000);
        Order order4 = new Order("George", 3, 22, Action.SELL, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        matcher.trade(order4);
        assertEquals(matcher.getToSell().size(), 1);
    }

    @Test
    void noSelfTrading() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 16, 8, Action.BUY, 1000);
        Order order3 = new Order("Nic", 15, 15, Action.SELL, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        assertEquals(matcher.getToSell().size(), 1);
        assertEquals(matcher.getToBuy().size(), 1);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 12);
        assertEquals(matcher.getToSell().get(0).getQuantity(), 7);
    }

    @Test
    void fullTradeNoTrade() {
        Order order1 = new Order("Nic", 5, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 16, 8, Action.BUY, 1000);
        Order order3 = new Order("George", 15, 15, Action.SELL, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        assertEquals(matcher.getToSell().size(), 1);
        assertEquals(matcher.getToBuy().size(), 1);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 12);
        assertEquals(matcher.getToSell().get(0).getQuantity(), 7);
    }

    @Test
    void exactQuantity() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nicholas", 6, 30, Action.SELL, 1000);
        Order order3 = new Order("George", 12, 12, Action.BUY, 1000);
        matcher.trade(order1);
        matcher.trade(order2);
        matcher.trade(order3);
        assertEquals(matcher.getToSell().size(), 1);
        assertEquals(matcher.getToBuy().size(), 0);
        assertEquals(matcher.getToSell().get(0).getQuantity(), 6);
        assertEquals(matcher.getTradeList().get(0).getQuantity(), 12);
        assertEquals(matcher.getTradeList().get(1).getPrice(), 6);
    }

    @Test
    void invalidPrice(){
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("George", 12, 13, Action.BUY, 1000);
        Order order3 = new Order("Nicholas", 18, 14, Action.SELL, 1000);
        matcher.trade(order2);
        matcher.trade(order1);
        matcher.trade(order3);
        assertEquals(matcher.getToSell().size(), 1);
        assertEquals(matcher.getToBuy().size(), 2);
        assertEquals(matcher.getToSell().get(0).getQuantity(), 14);
        assertEquals(matcher.getToBuy().get(0).getQuantity(), 12);
        assertEquals(matcher.getToBuy().get(1).getQuantity(), 13);
    }

    @Test
    void privateOrderBook() {
        Order order1 = new Order("Nic", 15, 12, Action.BUY, 1000);
        Order order2 = new Order("Nic", 11, 6, Action.SELL, 1000);
        Order order3 = new Order("Louis", 18, 3, Action.BUY, 1000);
        Order order4 = new Order("Nic", 9, 17, Action.BUY, 1000);
        matcher.trade(order2);
        matcher.trade(order1);
        matcher.trade(order3);
        matcher.trade(order4);
        matcher.privateOrderBook("Nic");
        assertEquals(matcher.oneAccount.size(), 4);
        assertEquals(matcher.oneAccount.get(2).getQuantity(), 3);

    }
}