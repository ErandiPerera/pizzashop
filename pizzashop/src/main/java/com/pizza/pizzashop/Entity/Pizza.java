package com.pizza.pizzashop.Entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza.pizzashop.Enum.Cheese;
import com.pizza.pizzashop.Enum.Crust;
import com.pizza.pizzashop.Enum.Sauce;
import com.pizza.pizzashop.Enum.Size;
import com.pizza.pizzashop.Enum.Topping;

import java.util.Arrays;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Crust crust;

    @Enumerated(EnumType.STRING)
    private Sauce sauce;

    @Column(columnDefinition = "TEXT")
    private String toppings;

    @Enumerated(EnumType.STRING)
    private Cheese cheese;

    @Enumerated(EnumType.STRING)
    private Size size;

    private double price;

    private boolean favourite;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public String getToppings() {
        return toppings;
    }


    public void setToppings(List<Topping> toppings) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> toppingNames = toppings.stream().map(Topping::name).collect(Collectors.toList());
           this.toppings = mapper.writeValueAsString(toppingNames);
       } catch(JsonProcessingException e) {
           this.toppings = null;
       }
    }

    public List<Topping> getToppingList() {
        ObjectMapper mapper = new ObjectMapper();
          try {
              String[] toppingNames = mapper.readValue(this.toppings, String[].class);
               return  Arrays.stream(toppingNames)
                       .map(Topping::valueOf)
                        .collect(Collectors.toList());
          } catch (Exception e) {
                return null;
           }

    }

    public Cheese getCheese() {
        return cheese;
    }

    public void setCheese(Cheese cheese) {
        this.cheese = cheese;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Builder for Pizza
    public static class Builder {
        private final Pizza pizza;

        public Builder() {
            pizza = new Pizza();
        }
        public Builder setName(String name) {
            pizza.setName(name);
            return this;
        }
        public Builder setCrust(Crust crust) {
            pizza.setCrust(crust);
            return this;
        }
        public Builder setSauce(Sauce sauce) {
            pizza.setSauce(sauce);
            return this;
        }
        public Builder setToppings(List<Topping> toppings) {
            pizza.setToppings(toppings);
            return this;
        }
        public Builder setCheese(Cheese cheese) {
            pizza.setCheese(cheese);
            return this;
        }

        public Builder setSize(Size size) {
            pizza.setSize(size);
            return this;
        }

        public Builder setFavourite(boolean favourite) {
            pizza.setFavourite(favourite);
            return this;
        }

        public Builder setUser(User user) {
            pizza.setUser(user);
            return this;
        }
        public Pizza build() {
            double calculatedPrice = 0;

            if (pizza.getSize() != null) {
                calculatedPrice += pizza.getSize().getPrice();
            }
            if (pizza.getCrust() != null) {
                calculatedPrice += pizza.getCrust().getPrice();
            }
            if (pizza.getSauce() != null) {
                calculatedPrice += pizza.getSauce().getPrice();
            }
            if (pizza.getToppingList() != null) {
                calculatedPrice += pizza.getToppingList().stream().mapToDouble(Topping::getPrice).sum();
            }
            if (pizza.getCheese() != null) {
                calculatedPrice += pizza.getCheese().getPrice();
            }

            pizza.setPrice(calculatedPrice);
            return pizza;
        }
    }
}