package com.pizza.pizzashop.Strategy;

import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Enum.Size;

public class SizeDiscountPromotion implements PromotionStrategy {

    @Override
    public double applyPromotion(Pizza pizza) {
        //10% discount for large pizzas 
        if (pizza.getSize() == Size.LARGE) {
            System.out.println("Applying 10% discount for Large pizza.");
            return pizza.getPrice() * 0.10;  
        }
        return 0;
    }
}
