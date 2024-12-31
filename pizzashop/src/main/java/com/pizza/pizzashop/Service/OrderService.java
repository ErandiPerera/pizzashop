package com.pizza.pizzashop.Service;

import java.util.Scanner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.pizzashop.Chain.CustomizationHandler;
import com.pizza.pizzashop.Chain.CustomizationRequest;
import com.pizza.pizzashop.Chain.PackagingHandler;
import com.pizza.pizzashop.Chain.ToppingHandler;
import com.pizza.pizzashop.Command.ActionManager;
import com.pizza.pizzashop.Command.FeedbackCommand;
import com.pizza.pizzashop.Command.PlaceOrderCommand;
import com.pizza.pizzashop.Entity.Feedback;
import com.pizza.pizzashop.Entity.Loyalty;
import com.pizza.pizzashop.Entity.Order;
import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Entity.User;
import com.pizza.pizzashop.Observer.Customer;
import com.pizza.pizzashop.Observer.OrderTrackingSystem;
import com.pizza.pizzashop.Repository.LoyaltyRepository;
import com.pizza.pizzashop.Repository.OrderRepository;
import com.pizza.pizzashop.Strategy.CreditCardPayment;
import com.pizza.pizzashop.Strategy.DigitalWallet;
import com.pizza.pizzashop.Strategy.LoyaltyPoints;
import com.pizza.pizzashop.Strategy.PaymentContext;
import com.pizza.pizzashop.Strategy.PaymentStrategy;
import com.pizza.pizzashop.Strategy.PromotionContext;
import com.pizza.pizzashop.Strategy.PromotionStrategy;
import com.pizza.pizzashop.Strategy.SeasonalHolidayPromotion;
import com.pizza.pizzashop.Strategy.SizeDiscountPromotion;
import com.pizza.pizzashop.Strategy.ToppingDiscountPromotion;

@Service
public class OrderService {

    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private LoyaltyRepository loyaltyRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ActionManager actionManager;
    
    
    //Place Order
    public void placeOrder(User loggedInUser, Pizza selectedPizza) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want extra toppings? (yes/no)");
        boolean extraToppings = "yes".equalsIgnoreCase(scanner.nextLine());

        System.out.println("Do you want special packaging? (yes/no)");
        boolean specialPackaging = "yes".equalsIgnoreCase(scanner.nextLine());

        CustomizationRequest request = new CustomizationRequest(extraToppings, specialPackaging);

        // Create and configure the chain of responsibility
        CustomizationHandler toppingHandler = new ToppingHandler();
        CustomizationHandler packagingHandler = new PackagingHandler();
        toppingHandler.setNextHandler(packagingHandler);

        
        Pizza customizedPizza = toppingHandler.handleCustomization(selectedPizza, request);

        
        System.out.println("Pizza Description: " + customizedPizza.getName());
        System.out.println("Pizza Cost: Rs." + customizedPizza.getPrice());

       // apply for promotions
        System.out.println("Do you want to apply a seasonal promotion? (yes/no)");
        String applyPromotion = scanner.nextLine();
        double discount = 0;

        if (applyPromotion.equalsIgnoreCase("yes")) {
            System.out.println("Choose the promotions to apply:");
            System.out.println("1. Seasonal Holiday Promotion - Get 15% Discount for Month of December");
            System.out.println("2. Topping Discount Promotion - Get 5% discount for Olive topping");
            System.out.println("3. Pizza Size Discount Promotion - Get 10% discount for Large pizza.");
            System.out.println("Enter the promotion numbers :");
            String promotionChoice = scanner.nextLine();
            String[] selectedPromotions = promotionChoice.split(",");
            
            for (String promotion : selectedPromotions) {
                PromotionStrategy promotionStrategy = null;
                switch (promotion.trim()) {
                    case "1":
                        promotionStrategy = new SeasonalHolidayPromotion();
                        break;
                    case "2":
                        promotionStrategy = new ToppingDiscountPromotion();
                        break;
                    case "3":
                        promotionStrategy = new SizeDiscountPromotion();
                        break;
                    default:
                        System.out.println("Invalid promotion choice: " + promotion);
                        continue;
                }
                
                PromotionContext promotionContext = new PromotionContext(promotionStrategy);
                discount += promotionContext.executePromotion(selectedPizza);
            }
        }

        double finalPrice = customizedPizza.getPrice() - discount;
        System.out.println("Pizza Price after promotion: Rs." + finalPrice);
        
        calculateAndUpdateLoyaltyPoints(loggedInUser, finalPrice, selectedPizza);     
        
    }

 // calculating, updating loyalty points and applying loyalty Discounts
    private void calculateAndUpdateLoyaltyPoints(User loggedInUser, double finalPrice, Pizza selectedPizza ) {
    	Scanner scanner = new Scanner(System.in);       
        Loyalty loyalty = loyaltyRepository.findByUser(loggedInUser);
        double loyaltyDiscount = 0;
        if (loyalty != null && loyalty.getPoints() > 0) {
            System.out.println("You have " + loyalty.getPoints() + " loyalty points.");
            System.out.println("Each point gives a discount of 1.");
            System.out.println("Please Enter the number of points to redeem:");
            int pointsToRedeem = scanner.nextInt();           
            if (pointsToRedeem > loyalty.getPoints()) {
                System.out.println("You don't have enough points. Using all available points.");
                pointsToRedeem = loyalty.getPoints();
            }
            loyaltyDiscount = pointsToRedeem * 1; 
            finalPrice -= loyaltyDiscount;

            // Deduction of redeemed points
            loyalty.setPoints(loyalty.getPoints() - pointsToRedeem);
            loyaltyRepository.save(loyalty);
            System.out.println("Loyalty discount applied: Rs." + loyaltyDiscount);
            System.out.println("Remaining points: " + loyalty.getPoints());
        }
        System.out.println("Price after loyalty discount: Rs." + finalPrice);
        // Calculate new loyalty points earned
        int pointsEarned = (int) finalPrice / 1000;
        System.out.println("You have earned: " + pointsEarned + " points for this order.");
        if (loyalty == null) {
        	//if user doesen't have an account, creating new account
            loyalty = new Loyalty();
            loyalty.setUser(loggedInUser);
            loyalty.setPoints(pointsEarned);
            loyaltyRepository.save(loyalty);
            System.out.println("Loyalty account created. Points earned: " + pointsEarned);
        } else {
            // Updating existing loyalty account
            loyalty.setPoints(loyalty.getPoints() + pointsEarned);
            loyaltyRepository.save(loyalty);
            System.out.println("Loyalty points updated. Total points: " + loyalty.getPoints());
        }
        System.out.println("Final price after applying all discounts: Rs." + finalPrice);
        scanner.nextLine();
        
              
        
        System.out.println("Enter delivery address: ");
        String address = scanner.nextLine();

        System.out.println("Enter contact number: ");
        String contactNo = scanner.nextLine();

        System.out.println("Select delivery type: 1. Home Delivery, 2. Pickup");
        int deliveryType = scanner.nextInt();
         scanner.nextLine();

        
        // Create and save the order
        Order order = new Order();
        order.setAddress(address);
        order.setContactNo(contactNo);
        order.setDeliveryType(deliveryType);
        order.setPizza(selectedPizza);
        order.setUser(loggedInUser);
        order.setPizzaPrice(finalPrice);  


        try {
            PlaceOrderCommand placeOrderCommand = new PlaceOrderCommand(order, orderRepository);
            actionManager.executeAction(placeOrderCommand);
        } catch (Exception e) {
            System.err.println("Error while placing order: " + e.getMessage());
            e.printStackTrace();
        }
        


        proceedWithPayment(loggedInUser, selectedPizza, order, finalPrice);
        
    
    }
    
      
    
    // payment logic 
    private void proceedWithPayment(User loggedInUser, Pizza selectedPizza, Order order, double finalPrice) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select payment method: 1. Credit Card, 2. Digital Wallet, 3. Loyalty Points");
        int paymentChoice = scanner.nextInt();
        PaymentStrategy paymentStrategy = null;

        switch (paymentChoice) {
            case 1:
                paymentStrategy = new CreditCardPayment();
                break;
            case 2:
                paymentStrategy = new DigitalWallet();
                break;
            case 3:
                paymentStrategy = new LoyaltyPoints();
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Credit Card.");
                paymentStrategy = new CreditCardPayment();
                break;
        }

        
        PaymentContext paymentContext = new PaymentContext(paymentStrategy);
        paymentContext.executePayment((int) finalPrice); 
        
        Customer customer = new Customer(loggedInUser.getfName(), loggedInUser.getEmail());
        orderTrackingSystem.registerObserver(customer);

        
        simulateOrderProgress(order.getId(), order); 

 
        orderTrackingSystem.removeObserver(customer);
    }

    
    private final OrderTrackingSystem orderTrackingSystem = new OrderTrackingSystem();

    public OrderTrackingSystem getOrderTrackingSystem() {
        return orderTrackingSystem;
    }
    
    // Simulating order progress
    private void simulateOrderProgress(int orderId, Order order) {
        try {
            Thread.sleep(3000);
            orderTrackingSystem.updateOrderStatus(orderId, "Preparing your pizza");

            Thread.sleep(3000); 
            orderTrackingSystem.updateOrderStatus(orderId, "Pizza baked");

            Thread.sleep(3000); 
            orderTrackingSystem.updateOrderStatus(orderId, "Out for delivery");

            Thread.sleep(3000); 
            orderTrackingSystem.updateOrderStatus(orderId, "Delivered");
        } catch (InterruptedException e) {
            System.err.println("Error in order simulation: " + e.getMessage());
        }
        
        
        askForFeedback(order);
    }
    
    
    // Adding Feedbacks
    private void askForFeedback (Order order) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to provide feedback for your order? (yes/no)");
        String feedbackChoice = scanner.next();
        

        if ("yes".equalsIgnoreCase(feedbackChoice)) {
        	scanner.nextLine();
            System.out.println("Enter your feedback comment:");
            String description = scanner.nextLine();

            System.out.println("Enter your rating (1-5):");
            int ratings = scanner.nextInt();

            
            Feedback feedback = new Feedback();
            feedback.setOrder(order); 
            feedback.setDescription(description);
            feedback.setRatings(ratings);

           
            feedbackService.saveFeedback(feedback);
            
            FeedbackCommand provideFeedbackCommand = new FeedbackCommand(feedback, feedbackService);
            actionManager.executeAction(provideFeedbackCommand);

            System.out.println("Thank you for your feedback!");
        }
    }
}
