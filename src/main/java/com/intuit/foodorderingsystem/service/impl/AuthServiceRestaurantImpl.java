package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.model.request.LoginRequest;
import com.intuit.foodorderingsystem.model.response.LoginResponse;
import com.intuit.foodorderingsystem.service.AuthService;
import com.intuit.foodorderingsystem.service.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("AuthServiceRestaurant")
@Log4j2
public class AuthServiceRestaurantImpl implements AuthService {

    private final JWTTokenService jwtTokenService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        //jwtTokenService.createJWTToken()
        return null;
    }
}
