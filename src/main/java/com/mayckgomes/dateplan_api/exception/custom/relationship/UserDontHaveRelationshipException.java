package com.mayckgomes.dateplan_api.exception.custom.relationship;

public class UserDontHaveRelationshipException extends RuntimeException {
    public UserDontHaveRelationshipException() {
        super("user dont have relationship");
    }
}
