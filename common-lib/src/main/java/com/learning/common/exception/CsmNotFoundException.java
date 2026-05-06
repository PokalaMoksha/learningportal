package com.learning.common.exception;

public class CsmNotFoundException
       extends RuntimeException {

    public CsmNotFoundException() {
        super("CSM not found!");
    }

    public CsmNotFoundException(String message) {
        super(message);
    }
}