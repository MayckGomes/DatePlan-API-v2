package com.mayckgomes.dateplan_api.exception.custom.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
