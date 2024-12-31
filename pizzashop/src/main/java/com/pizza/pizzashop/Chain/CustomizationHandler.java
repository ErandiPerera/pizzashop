package com.pizza.pizzashop.Chain;

import com.pizza.pizzashop.Entity.Pizza;

public abstract class CustomizationHandler {
    protected CustomizationHandler nextHandler;

    public void setNextHandler(CustomizationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract Pizza handleCustomization(Pizza pizza, CustomizationRequest request);
}