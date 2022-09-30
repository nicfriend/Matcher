package com.example.Matcher;

import javax.validation.constraints.Positive;

public class Aggregate {
    @Positive(message = "price must be greater than 0")
    int price;
    @Positive(message = "quantity must be greater than 0")
    int quantity;

    public Aggregate(int price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return "[Price: " + this.price + ", Quantity: " + this.quantity + "]" + "\n";
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
