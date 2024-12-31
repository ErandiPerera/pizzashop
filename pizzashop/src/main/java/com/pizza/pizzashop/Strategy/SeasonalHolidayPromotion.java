package com.pizza.pizzashop.Strategy;

import java.time.Month;

import com.pizza.pizzashop.Entity.Pizza;
import java.time.LocalDate;

public class SeasonalHolidayPromotion implements PromotionStrategy {

    @Override
    public double applyPromotion(Pizza pizza) {
        // 15% discount for December 
        Month currentMonth = LocalDate.now().getMonth();
        if (currentMonth == Month.DECEMBER) {
            System.out.println("Applying 15% holiday discount.");
            return pizza.getPrice() * 0.15;  
        }
        return 0;
    }
}
