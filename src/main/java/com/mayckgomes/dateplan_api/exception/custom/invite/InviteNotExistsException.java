package com.mayckgomes.dateplan_api.exception.custom.invite;

public class InviteNotExistsException extends RuntimeException {
    public InviteNotExistsException() {
        super("Invite Not Exists");
    }
}
