package com.mayckgomes.dateplan_api.exception.custom.token;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token is expired");
    }

    public TokenExpiredException(String message) {
        super(message);
    }

}
