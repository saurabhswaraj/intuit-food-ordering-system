package com.intuit.foodorderingsystem.service;

import com.intuit.foodorderingsystem.model.request.LoginRequest;
import com.intuit.foodorderingsystem.model.response.LoginResponse;

public interface AuthService {

    LoginResponse login (LoginRequest loginRequest);

}
