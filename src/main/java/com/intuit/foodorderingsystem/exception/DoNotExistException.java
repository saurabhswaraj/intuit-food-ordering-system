package com.intuit.foodorderingsystem.exception;

public class DoNotExistException extends RuntimeException{
    public DoNotExistException(String message) {
        super(message);
    }
}
