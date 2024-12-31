package com.pizza.pizzashop.Strategy;

public class LoyaltyPoints implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid Rs." + amount + " using Loyalty Points.");
    }
}
