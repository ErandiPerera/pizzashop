package com.pizza.pizzashop.Decorator;

import com.pizza.pizzashop.Entity.Pizza;

public class ToppingDecorator extends PizzaDecorator {
    private final double toppingCost = 200.00;

    public ToppingDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public double getPrice() {
        return pizza.getPrice() + toppingCost;
    }

    @Override
    public String getName() {
        return pizza.getName() + " + Special Topping";
    }
}
