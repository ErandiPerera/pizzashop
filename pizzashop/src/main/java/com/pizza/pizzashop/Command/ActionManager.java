package com.pizza.pizzashop.Command;

import org.springframework.stereotype.Component;
import java.util.Stack;

@Component
public class ActionManager {

    private Stack<UserActionCommand> commandHistory = new Stack<>();

    public void executeAction(UserActionCommand command) {
        command.execute();
        commandHistory.push(command);
    }
}
