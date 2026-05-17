package com.mayckgomes.dateplan_api.exception.custom.invite;

public class UserHaveARelationshipException extends RuntimeException {
    public UserHaveARelationshipException(String user) {
        super("the " + user + " user is already in relationship");
    }

    public UserHaveARelationshipException() {
        super("the user have a relationship");
    }
}
