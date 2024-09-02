package com.intuit.foodorderingsystem.model;

import lombok.*;

@Value
@Builder
@ToString
@Getter
@Setter
public class ErrorMessageCommon implements ErrorMessage{
    private String message;
}
