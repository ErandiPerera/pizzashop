package com.pizza.pizzashop.Strategy;

import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Enum.Topping;

public class ToppingDiscountPromotion implements PromotionStrategy {

    @Override
    public double applyPromotion(Pizza pizza) {
        // Example: Apply a 5% discount if 'Olives' toppings is selected
        boolean hasOlives = pizza.getToppingList().contains(Topping.OLIVES);
        if (hasOlives) {
            System.out.println("Applying 5% discount for Olive topping.");
            return pizza.getPrice() * 0.05;  // 5% discount
        }
        return 0;
    }
}