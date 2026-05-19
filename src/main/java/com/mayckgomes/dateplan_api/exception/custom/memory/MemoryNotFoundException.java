package com.mayckgomes.dateplan_api.exception.custom.memory;

public class MemoryNotFoundException extends RuntimeException {
    public MemoryNotFoundException() {
        super("this memory not exists");
    }
}
