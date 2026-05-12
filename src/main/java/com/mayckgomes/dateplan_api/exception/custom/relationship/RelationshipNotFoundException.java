package com.mayckgomes.dateplan_api.exception.custom.relationship;

public class RelationshipNotFoundException extends RuntimeException {
    public RelationshipNotFoundException() {
        super("Relationship not found");
    }
}
