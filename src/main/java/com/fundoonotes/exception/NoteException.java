package com.fundoonotes.exception;

public class NoteException extends RuntimeException {
    // [Prajwal]:UC8:Specific custom exception representing a fault with Note processing
    public NoteException(String message) {
        super(message);
    }
}
