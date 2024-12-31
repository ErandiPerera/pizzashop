package com.pizza.pizzashop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizza.pizzashop.Entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
   
}
