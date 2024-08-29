package com.intuit.foodorderingsystem.exception;

public class IneligibleRequestException extends RuntimeException{
    public IneligibleRequestException(String message) {
        super(message);
    }
}
