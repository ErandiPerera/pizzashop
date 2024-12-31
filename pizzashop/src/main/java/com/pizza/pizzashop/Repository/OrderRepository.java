package com.pizza.pizzashop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizza.pizzashop.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
}