package com.example.Matcher.entities;

public class Trade {
    String sellAcc;
    String buyAcc;
    int quantity;
    int price;
    int counter;

    public Trade(String sellAcc, String buyAcc, int quantity, int price, int counter) {
        this.sellAcc = sellAcc;
        this.buyAcc = buyAcc;
        this.quantity = quantity;
        this.price = price;
        this.counter = counter;
    }

    public String toString() {
        return "{SellAcc: " + this.sellAcc + ", BuyAcc: " + this.buyAcc + ", Quantity: " + this.quantity + ", Price: " + this.price + "}" + "\n";
    }

    public String getSellAcc() {
        return sellAcc;
    }

    public String getBuyAcc() {
        return buyAcc;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getCounter() {
        return counter;
    }
}
