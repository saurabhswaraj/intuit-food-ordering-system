package com.intuit.foodorderingsystem.exception;

public class PhoneNumberNotValidException extends RuntimeException{
    public PhoneNumberNotValidException(String message) {
        super(message);
    }
}
