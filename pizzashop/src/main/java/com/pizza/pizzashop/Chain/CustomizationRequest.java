package com.pizza.pizzashop.Chain;

public class CustomizationRequest {
    private boolean extraToppings;
    private boolean specialPackaging;

    public CustomizationRequest(boolean extraToppings, boolean specialPackaging) {
        this.extraToppings = extraToppings;
        this.specialPackaging = specialPackaging;
    }

    public boolean isExtraToppings() {
        return extraToppings;
    }

    public boolean isSpecialPackaging() {
        return specialPackaging;
    }
}
