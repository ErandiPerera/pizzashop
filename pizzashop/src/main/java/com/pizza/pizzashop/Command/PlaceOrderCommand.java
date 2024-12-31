package com.pizza.pizzashop.Command;

import com.pizza.pizzashop.Entity.Order;
import com.pizza.pizzashop.Repository.OrderRepository;

public class PlaceOrderCommand implements UserActionCommand {

    private Order order;
    private OrderRepository orderRepository;

    public PlaceOrderCommand(Order order, OrderRepository orderRepository) {
        this.order = order;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute() {
        orderRepository.save(order); 
        System.out.println("Order placed and saved: " + order.getPizza().getName());
    }
}