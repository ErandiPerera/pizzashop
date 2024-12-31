package com.pizza.pizzashop.Strategy;

public class DigitalWallet implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid Rs." + amount + " using Digital Wallet.");
    }
}
