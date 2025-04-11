package com.zidiogroup9.expensemanagement.services.Impl;


import com.zidiogroup9.expensemanagement.services.ForgetPasswordService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    @Value("${jwt.secretKey}")
    private String jwtSecreteKey;
    public SecretKey generateSecreteKey() {
        return Keys.hmacShaKeyFor(jwtSecreteKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String createForgetPasswordToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 minutes
                .signWith(generateSecreteKey())
                .compact();
    }

    @Override
    public String generateEmailToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(generateSecreteKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
