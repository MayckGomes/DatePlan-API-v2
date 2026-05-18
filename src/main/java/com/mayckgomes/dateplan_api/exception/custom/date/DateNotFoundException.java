package com.mayckgomes.dateplan_api.exception.custom.date;

public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException() {
        super("this date not exists");
    }
}
