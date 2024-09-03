package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.enums.Role;
import com.intuit.foodorderingsystem.model.JWTDecodeModel;
import com.intuit.foodorderingsystem.service.JWTTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTTokenServiceImpl implements JWTTokenService {

    private final SecretKeySpec key;

    public JWTTokenServiceImpl(@Value("${jwt.token.sign-key}") String signKey) {
        key = new SecretKeySpec(Base64.getDecoder().decode(signKey), Jwts.SIG.HS256.getId());
    }

    @Override
    public String createJWTToken(Long id, Role role, ZonedDateTime creationDate) {
        return Jwts.builder()
                .subject(role.getCode())
                .id(id.toString())
                .expiration(Date.from(creationDate.plusDays(1L).toInstant()))
                .issuedAt(Date.from(creationDate.toInstant()))
                .signWith(key).compact();
    }

    @Override
    public JWTDecodeModel decodeJWTToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parser().decryptWith(key).build().parseSignedClaims(jwtToken);
        return JWTDecodeModel.builder()
                .id(Long.valueOf(claims.getPayload().getId()))
                .role(Role.valueOf(claims.getPayload().getSubject()))
                .issuedAt(ZonedDateTime.ofInstant(claims.getPayload().getIssuedAt().toInstant(), ZoneId.systemDefault()))
                .expiresAt(ZonedDateTime.ofInstant(claims.getPayload().getExpiration().toInstant(), ZoneId.systemDefault()))
                .build();
    }
}
