package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.model.request.LoginRequest;
import com.intuit.foodorderingsystem.model.response.LoginResponse;
import com.intuit.foodorderingsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("AuthServiceUser")
@Log4j2
public class AuthServiceUserImpl implements AuthService {
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
