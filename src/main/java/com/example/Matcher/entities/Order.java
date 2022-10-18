package com.example.Matcher.entities;

import com.example.Matcher.enums.Action;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Table(name="orderList")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @NotEmpty
    @Column
    private String account;

    @Positive
    @Column
    private int price;

    @Positive
    @Column
    private int quantity;

    @NotNull
    @Column
    private Action action;

    @Id
    @Column
    private Integer counter;

    public String toString(){
        return "[Account: " + this.account + ", Price: " + this.price + ", Quantity: " + this.quantity + ", Action: " + this.action + ", Counter: " + this.counter + "]" + "\n";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public void setAction(Action action) { this.action = action; }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
