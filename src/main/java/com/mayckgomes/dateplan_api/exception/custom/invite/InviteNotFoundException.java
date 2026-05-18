package com.mayckgomes.dateplan_api.exception.custom.invite;

public class InviteNotFoundException extends RuntimeException {
    public InviteNotFoundException() {
        super("Invite Not Exists");
    }
}
