package com.pizza.pizzashop;

import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Entity.User;
import com.pizza.pizzashop.Service.UserService;
import com.pizza.pizzashop.Service.OrderService;
import com.pizza.pizzashop.Service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private PizzaService pizzaService;
    
    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Pizza Shop");
        System.out.println("1. Sign Up\n2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine();

        User currentUser = null;

        if (choice == 1) {
            System.out.println("Enter first name: ");
            String fName = scanner.nextLine();
            System.out.println("Enter last name: ");
            String lName = scanner.nextLine();
            System.out.println("Enter email: ");
            String email = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            System.out.println("Are you an Admin? (yes/no): ");
            String isAdmin = scanner.nextLine();
            int userType = isAdmin.equalsIgnoreCase("yes") ? 1 : 0;

            User user = new User();
            user.setfName(fName);
            user.setlName(lName);
            user.setEmail(email);
            user.setPassword(password);
            user.setUserType(userType);

            currentUser = userService.registerUser(user);
            System.out.println("User registered successfully!");
        } else if (choice == 2) {
            System.out.println("Enter email: ");
            String email = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();

            currentUser = userService.loginUser(email, password);
            if (currentUser == null) {
                System.out.println("Invalid credentials.");
                return;
            }
            System.out.println("Welcome, " + currentUser.getfName());
        }

        if (currentUser != null && currentUser.getUserType() == 0) { 
            pizzaMenu(currentUser);
        }
    }

    public void pizzaMenu(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
        	System.out.println("\nPizza Menu:");
            System.out.println("1. Create Pizza");
            System.out.println("2. View My Pizzas");
            System.out.println("3. Change Favorite Pizza");
            System.out.println("4. Place Order");  
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> pizzaService.createPizza(loggedInUser);
                case 2 -> {
                    System.out.println("Your Pizzas:");
                    pizzaService.getPizzasByUser(loggedInUser);
                }
                case 3 -> pizzaService.toggleFavorite(loggedInUser);
                case 4 -> placeOrderMenu(loggedInUser);  
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 5);
    }

    public void placeOrderMenu(User loggedInUser) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a pizza to order (Enter Pizza ID): ");
        int pizzaId = scanner.nextInt();

        
        Pizza selectedPizza = pizzaService.getPizzasByUser(loggedInUser).stream()
                .filter(pizza -> pizza.getId() == pizzaId)
                .findFirst()
                .orElse(null);

        if (selectedPizza == null) {
            System.out.println("Invalid pizza ID. Please try again.");
            return;
        }

        orderService.placeOrder(loggedInUser, selectedPizza);  // Place the order
    }
}
