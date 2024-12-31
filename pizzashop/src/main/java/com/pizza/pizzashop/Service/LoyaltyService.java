package com.pizza.pizzashop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.pizzashop.Entity.Loyalty;
import com.pizza.pizzashop.Entity.User;
import com.pizza.pizzashop.Repository.LoyaltyRepository;

@Service
public class LoyaltyService {

    @Autowired
    private LoyaltyRepository loyaltyRepository;

    public Loyalty getLoyaltyByUser(User user) {
        return loyaltyRepository.findByUser(user); 
    }

    public void updateLoyaltyPoints(User user, int points) {
        Loyalty loyalty = getLoyaltyByUser(user);
        if (loyalty != null) {
           
            loyalty.setPoints(loyalty.getPoints() + points);
        } else {
            loyalty = new Loyalty();
            loyalty.setUser(user);
            loyalty.setPoints(points); 
        }
        loyaltyRepository.save(loyalty); 
    }
}


