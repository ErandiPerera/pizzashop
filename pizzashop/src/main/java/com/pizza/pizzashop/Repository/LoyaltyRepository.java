package com.pizza.pizzashop.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizza.pizzashop.Entity.Loyalty;
import com.pizza.pizzashop.Entity.User;



public interface LoyaltyRepository extends JpaRepository<Loyalty, Integer> {
	 Loyalty findByUser(User user);
	
}