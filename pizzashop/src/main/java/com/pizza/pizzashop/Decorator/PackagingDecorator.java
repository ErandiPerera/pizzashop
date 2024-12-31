package com.pizza.pizzashop.Decorator;

import com.pizza.pizzashop.Entity.Pizza;

public class PackagingDecorator extends PizzaDecorator {
    private final double packagingCost = 50.00;

    public PackagingDecorator(Pizza pizza) {
        super(pizza); 
    }

    @Override
    public double getPrice() {
        
        return pizza.getPrice() + packagingCost;
    }

    @Override
    public String getName() {
       
        return pizza.getName() + " + Special Packaging";
    }
}

