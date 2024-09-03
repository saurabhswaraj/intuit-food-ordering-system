package com.intuit.foodorderingsystem.model;

import com.intuit.foodorderingsystem.enums.Role;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Builder
@Value
public class JWTDecodeModel {

    Role role;
    Long id;
    ZonedDateTime issuedAt;
    ZonedDateTime expiresAt;
}
