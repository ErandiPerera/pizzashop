package com.pizza.pizzashop.Decorator;

import com.pizza.pizzashop.Entity.Pizza;

public abstract class PizzaDecorator extends Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public double getPrice() {
        return pizza.getPrice();
    }

    @Override
    public String getName() {
        return pizza.getName();
    }
}
