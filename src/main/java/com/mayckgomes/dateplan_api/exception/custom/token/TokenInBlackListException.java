package com.mayckgomes.dateplan_api.exception.custom.token;

public class TokenInBlackListException extends RuntimeException {
    public TokenInBlackListException() {
        super("The token is blacklisted.");
    }
}
