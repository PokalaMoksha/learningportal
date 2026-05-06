package com.learning.common.exception;

public class ManagerNotFoundException
       extends RuntimeException {

    public ManagerNotFoundException() {
        super("Manager not found!");
    }

    public ManagerNotFoundException(String message) {
        super(message);
    }
}
