package com.intuit.foodorderingsystem.model;

import lombok.*;

@Value
@Builder
@ToString
@Getter
@Setter
public class ErrorMessageCommon extends ErrorMessage{
    private String message;
}
