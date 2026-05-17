package com.mayckgomes.dateplan_api.exception.custom.invite;

public class NotDecisionMakerException extends RuntimeException {
    public NotDecisionMakerException() {
        super("You are not a decision maker of this invite");
    }
}
