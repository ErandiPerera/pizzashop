package com.pizza.pizzashop.Command;


import com.pizza.pizzashop.Entity.Feedback;
import com.pizza.pizzashop.Service.FeedbackService;

public class FeedbackCommand implements UserActionCommand {

    private Feedback feedback;
    private FeedbackService feedbackService;

    public FeedbackCommand(Feedback feedback, FeedbackService feedbackService) {
        this.feedback = feedback;
        this.feedbackService = feedbackService;
    }

    @Override
    public void execute() {
        feedbackService.saveFeedback(feedback);
        System.out.println("Feedback provided for Order: " + feedback.getDescription());
    }

}

