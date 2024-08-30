package com.intuit.foodorderingsystem.exception;

public class OrderCanNotBeCreatedException extends RuntimeException{
    public OrderCanNotBeCreatedException(String message) {
        super(message);
    }
}
