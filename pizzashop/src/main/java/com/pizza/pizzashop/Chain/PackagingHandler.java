package com.pizza.pizzashop.Chain;


import com.pizza.pizzashop.Decorator.PackagingDecorator;
import com.pizza.pizzashop.Entity.Pizza;

public class PackagingHandler extends CustomizationHandler {

    @Override
    public Pizza handleCustomization(Pizza pizza, CustomizationRequest request) {
        if (request.isSpecialPackaging()) {
            pizza = new PackagingDecorator(pizza); // Add special packaging
        }

        if (nextHandler != null) {
            return nextHandler.handleCustomization(pizza, request);
        }

        return pizza;
    }
}