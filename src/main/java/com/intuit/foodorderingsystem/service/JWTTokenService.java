package com.intuit.foodorderingsystem.service;

import com.intuit.foodorderingsystem.enums.Role;
import com.intuit.foodorderingsystem.model.JWTDecodeModel;

import java.time.ZonedDateTime;

public interface JWTTokenService {

    String createJWTToken(Long id, Role role, ZonedDateTime creationDate);

    JWTDecodeModel decodeJWTToken(String jwtToken);
}
