package com.pizza.pizzashop.Service;


import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Entity.User;
import com.pizza.pizzashop.Enum.*;
import com.pizza.pizzashop.Repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public void createPizza(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("== Create Your Pizza Here ==");
        scanner.nextLine();

        System.out.print("Enter pizza name: ");
        String name = scanner.nextLine();
        
        System.out.println("Select size (Enter the Number): 1. Small, 2. Medium, 3. Large");
        Size size = Size.values()[scanner.nextInt() - 1];

        System.out.println("Select crust (Enter the Number): 1. Thin, 2. Thick, 3. Stuffed");
        Crust crust = Crust.values()[scanner.nextInt() - 1];

        System.out.println("Select sauce (Enter the Number): 1. Tomato, 2. Barbecue, 3. Pesto");
        Sauce sauce = Sauce.values()[scanner.nextInt() - 1];

        System.out.println("Select cheese (Enter the Number): 1. Mozzarella, 2. Cheddar, 3. Parmesan");
        Cheese cheese = Cheese.values()[scanner.nextInt() - 1];

        System.out.println("Select toppings (comma-separated numbers): 1. Olives, 2. Sausage, 3. Mushrooms, 4. Onions");
        scanner.nextLine();
        String[] toppingSelections = scanner.nextLine().split(",");
        List<Topping> toppings = List.of(toppingSelections)
                .stream()
                .map(selection -> Topping.values()[Integer.parseInt(selection.trim()) - 1])
                .collect(Collectors.toList());

        // Use Builder Pattern to create the pizza
        Pizza pizza = new Pizza.Builder()
                .setName(name)
                .setSize(size)
                .setCrust(crust)
                .setSauce(sauce)
                .setCheese(cheese)
                .setToppings(toppings)
                .setUser(loggedInUser)
                .build();

        pizzaRepository.save(pizza);
        System.out.println("Pizza created successfully: " + pizza.getName() + " - Price: " + pizza.getPrice());
    }

    public List<Pizza> getPizzasByUser(User loggedInUser) {
    	
    	System.out.println("== Your Pizza List ==");
        
        List<Pizza> pizzas = pizzaRepository.findAllByUser(loggedInUser);

       
        System.out.println("+----+----------------------+----------------------+----------------------+----------------------+--------------------------------+----------+---------+");
        System.out.println("| ID | Name                 | Crust                | Sauce                | Cheese               | Toppings                       | Price    | Favorite|");
        System.out.println("+----+----------------------+----------------------+----------------------+----------------------+--------------------------------+----------+---------+");
        pizzas.forEach(pizza -> {
            System.out.printf("| %-2d | %-20s | %-20s | %-20s | %-20s | %-30s | %-10s | %-7s |\n",
                    pizza.getId(),
                    pizza.getName(),
                    pizza.getCrust().name(), 
                    pizza.getSauce().name(), 
                    pizza.getCheese().name(), 
                    pizza.getToppingList().stream() 
                    .map(Topping::name)
                    .reduce((t1, t2) -> t1 + ", " + t2) 
                    .orElse("None"), 
                    pizza.getPrice(),
                    pizza.isFavourite() ? "Yes" : "No");
        });
        System.out.println("+----+----------------------+----------------------+----------------------+----------------------+--------------------------------+----------+---------+");

        
        return pizzas;
    }
    
    public void toggleFavorite(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== Add Your Pizza into Favourite List ==");
        List<Pizza> pizzas = getPizzasByUser(loggedInUser);

         if (pizzas.isEmpty()) {
            System.out.println("You have no pizzas to toggle the favorite status for.");
            return;
        }
        System.out.println("Select the ID of the pizza you want to toggle (or 0 to cancel):");
         int pizzaId = scanner.nextInt();
        scanner.nextLine();

        if (pizzaId == 0) {
            System.out.println("Operation cancelled");
            return;
        }


        Pizza selectedPizza = pizzas.stream()
                .filter(pizza -> pizza.getId() == pizzaId)
                .findFirst()
                .orElse(null);


        if (selectedPizza != null) {
            selectedPizza.setFavourite(!selectedPizza.isFavourite());
            pizzaRepository.save(selectedPizza);
             System.out.println("Pizza with id " + pizzaId + " favorite status changed to " + (selectedPizza.isFavourite() ? 
            		 "favorite" : "not favorite"));
        }else {
            System.out.println("Invalid pizza selection. Please enter a valid ID.");
        }


    }
}


