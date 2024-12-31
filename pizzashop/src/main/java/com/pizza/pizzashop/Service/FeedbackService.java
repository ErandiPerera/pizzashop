package com.pizza.pizzashop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizza.pizzashop.Entity.Feedback;
import com.pizza.pizzashop.Repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Method to save feedback
    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

  
}
