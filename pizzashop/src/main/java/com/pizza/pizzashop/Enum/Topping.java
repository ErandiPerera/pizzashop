package com.pizza.pizzashop.Enum;


import java.util.Arrays;
import java.util.List;

public enum Topping {
    OLIVES("Olives", 150),
    SAUSAGE("Sausage", 150),
    MUSHROOMS("Mushrooms", 150),
    ONIONS("Onions", 150);

    private final String name;
    private final double price;

    Topping(String name, double price) {
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
        return name + " - Price: LKR" + price;
    }

    public static List<Topping> getAllToppings() {
        return Arrays.asList(Topping.values());
    }
}

