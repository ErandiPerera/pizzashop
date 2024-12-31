package com.pizza.pizzashop.Repository;

import com.pizza.pizzashop.Entity.Pizza;
import com.pizza.pizzashop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
	List<Pizza> findAllByUser(User user);
}
