package com.pizza.pizzashop.Chain;


import com.pizza.pizzashop.Decorator.ToppingDecorator;
import com.pizza.pizzashop.Entity.Pizza;

public class ToppingHandler extends CustomizationHandler {

    @Override
    public Pizza handleCustomization(Pizza pizza, CustomizationRequest request) {
        if (request.isExtraToppings()) {
            pizza = new ToppingDecorator(pizza); // Add toppings
        }

        if (nextHandler != null) {
            return nextHandler.handleCustomization(pizza, request);
        }

        return pizza;
    }
}