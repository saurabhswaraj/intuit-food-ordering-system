package com.intuit.foodorderingsystem.model.response;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {

    String jwtToken;
    String refreshToken;
}
