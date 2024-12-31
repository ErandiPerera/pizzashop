package com.pizza.pizzashop.Enum;

import java.util.Arrays;
import java.util.List;

public enum Size {
    SMALL("Small", 1500),
    MEDIUM("Medium", 2000),
    LARGE("Large", 2500);
   

    private final String sizeName;
    private final double price;

   Size(String sizeName, double price) {
        this.sizeName = sizeName;
        this.price = price;
    }

    public String getSizeName() {
        return sizeName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return sizeName + " - Price: LKR" + price;
    }
    
    public static List<Size> getAllSize() {
        return Arrays.asList(Size.values());
    }
}
