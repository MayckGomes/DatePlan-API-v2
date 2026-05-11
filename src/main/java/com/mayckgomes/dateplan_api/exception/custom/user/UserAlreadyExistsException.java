package com.mayckgomes.dateplan_api.exception.custom.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User already exists.");
    }
}
