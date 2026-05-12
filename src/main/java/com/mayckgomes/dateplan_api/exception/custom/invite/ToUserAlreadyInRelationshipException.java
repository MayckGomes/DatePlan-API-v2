package com.mayckgomes.dateplan_api.exception.custom.invite;

public class ToUserAlreadyInRelationshipException extends RuntimeException {
    public ToUserAlreadyInRelationshipException(String user) {
        super("the " + user + " user is already in relationship");
    }
}
