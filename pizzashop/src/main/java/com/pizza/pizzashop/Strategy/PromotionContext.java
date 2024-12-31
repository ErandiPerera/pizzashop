package com.pizza.pizzashop.Strategy;

import com.pizza.pizzashop.Entity.Pizza;

public class PromotionContext {
    private PromotionStrategy promotionStrategy;

    public PromotionContext(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public double executePromotion(Pizza pizza) {
        return promotionStrategy.applyPromotion(pizza);
    }
}

