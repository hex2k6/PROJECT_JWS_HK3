package com.example.medic.exception;

public class ConflictException
        extends RuntimeException {

    public ConflictException(
            String message
    ) {

        super(message);
    }
}