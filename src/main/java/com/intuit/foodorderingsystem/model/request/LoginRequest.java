package com.intuit.foodorderingsystem.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRequest {

    String userId;
    String password;
}
