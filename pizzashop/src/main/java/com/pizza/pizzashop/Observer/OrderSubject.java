package com.pizza.pizzashop.Observer;

public interface OrderSubject {
    void registerObserver(OrderObserver observer);

    void removeObserver(OrderObserver observer);

    void notifyObservers(int orderId, String status);
}
