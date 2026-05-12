package com.mayckgomes.dateplan_api.exception.custom.relationship;

public class UserRelationshipNotFoundException extends RuntimeException {
    public UserRelationshipNotFoundException(String id) {
        super("the userId = " + id + " in this relationship is not found");
    }
}
