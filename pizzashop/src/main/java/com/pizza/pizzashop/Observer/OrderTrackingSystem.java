package com.pizza.pizzashop.Observer;

import java.util.ArrayList;

import java.util.List;

public class OrderTrackingSystem implements OrderSubject {
    private final List<OrderObserver> observers = new ArrayList<>();

    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int orderId, String status) {
        for (OrderObserver observer : observers) {
            observer.updateOrderStatus(orderId, status);
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        System.out.println("Order ID: " + orderId + " updated to status: " + status);
        notifyObservers(orderId, status);
    }
}

