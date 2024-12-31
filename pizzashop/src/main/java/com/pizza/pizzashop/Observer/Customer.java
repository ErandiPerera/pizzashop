package com.pizza.pizzashop.Observer;

public class Customer implements OrderObserver {
    private final String name;
    private final String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email= email;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        System.out.println("Notification for " + name + ": Your order " + orderId + " is now " + status);
    }
}
