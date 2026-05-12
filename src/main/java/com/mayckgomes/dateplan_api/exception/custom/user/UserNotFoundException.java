package com.mayckgomes.dateplan_api.exception.custom.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
    public UserNotFoundException(String user) {
        super("User " + user + " not found");
    }
}
