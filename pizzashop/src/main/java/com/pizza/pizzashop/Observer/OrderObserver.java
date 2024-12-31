package com.pizza.pizzashop.Observer;

public interface OrderObserver {
    void updateOrderStatus(int orderId, String status);
}