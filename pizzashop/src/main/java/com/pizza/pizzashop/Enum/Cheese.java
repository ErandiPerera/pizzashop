package com.pizza.pizzashop.Enum;

import java.util.Arrays;
import java.util.List;

public enum Cheese {
    MOZZARELLA("Mozzarella", 250),
    CHEDDAR("Cheddar", 250),
    PARMESAN("Parmesan", 250);

    private final String name;
    private final double price;

    Cheese(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - Price: $" + price;
    }

    public static List<Cheese> getAllCheeses() {
        return Arrays.asList(Cheese.values());
    }
}