package com.mayckgomes.dateplan_api.exception.custom.invite;

public class UserAlreadyInRelationshipException extends RuntimeException {
    public UserAlreadyInRelationshipException(String user) {
        super("the " + user + " user is already in relationship");
    }
}
