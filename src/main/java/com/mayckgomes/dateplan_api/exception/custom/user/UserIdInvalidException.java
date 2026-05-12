package com.mayckgomes.dateplan_api.exception.custom.user;

public class UserIdInvalidException extends RuntimeException {
    public UserIdInvalidException() {
        super("user id invalid");
    }
}
