package com.pizza.pizzashop.Strategy;

import com.pizza.pizzashop.Entity.Pizza;

public interface PromotionStrategy {
    double applyPromotion(Pizza pizza);
}
