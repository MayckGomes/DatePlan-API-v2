package com.mayckgomes.dateplan_api.exception.custom.token;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Token is invalid");
    }
}
