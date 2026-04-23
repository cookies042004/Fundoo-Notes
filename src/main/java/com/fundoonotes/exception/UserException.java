package com.fundoonotes.exception;

public class UserException extends RuntimeException {
    // UC4:Custom exception for User related logical errors
    public UserException(String message) {
        super(message);
    }
}
