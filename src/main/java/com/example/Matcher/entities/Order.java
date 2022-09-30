package com.example.Matcher.entities;

import com.example.Matcher.enums.Action;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Order {

    @NotEmpty
    private String account;

    @Positive(message = "price must be greater than 0")
    private int price;

    @Positive(message = "quantity must be greater than 0")
    private int quantity;

    @NotNull
    private Action action;
    private int counter;

    public Order(String account, int price, int quantity, Action action, int counter) {
        this.account = account;
        this.price = price;
        this.quantity = quantity;
        this.action = action;
        this.counter = counter;
    }

    public String toString(){
        return "[Account: " + this.account + ", Price: " + this.price + ", Quantity: " + this.quantity + ", Action: " + this.action + ", Counter: " + this.counter + "]" + "\n";
    }

    public String getAccount() {
        return account;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Action getAction() {
        return action;
    }

    public int getCounter() {
        return counter;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
