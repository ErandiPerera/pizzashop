package com.pizza.pizzashop.Enum;

import java.util.Arrays;
import java.util.List;

public enum Sauce {
    TOMATO_BASIL("Tomato Basil", 150),
    BBQ("BBQ", 150),
    PESTO("Pesto", 150);

    private final String name;
    private final double price;

    Sauce(String name, double price) {
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

    public static List<Sauce> getAllSauces() {
        return Arrays.asList(Sauce.values());
    }
}
