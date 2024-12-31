package com.pizza.pizzashop.Enum;

import java.util.Arrays;
import java.util.List;

public enum Crust {
    THIN_CRUST("Thin Crust", 300),
    THICK_CRUST("Thick Dish", 500),
	STUFFED_CRUST("Stuffed Crust", 400);

    private final String name;
    private final double price;

    Crust(String name, double price) {
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

    public static List<Crust> getAllCrusts() {
        return Arrays.asList(Crust.values());
    }
}
