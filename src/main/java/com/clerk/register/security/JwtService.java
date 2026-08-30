package com.clerk.register.security;

import com.clerk.register.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private static final String HMAC_SHA256 = "HmacSHA256";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(jwtProperties.secret().getBytes(), HMAC_SHA256);
    }

    public String issueJwt(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(Date.from(Instant.now()))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseJwt(String token) {
        JwtParserBuilder parser = Jwts.parser().verifyWith(secretKey);

        if (jwtProperties.acceptUnsignedTokens()) {
            parser.unsecured();
        }

        return (Claims) parser.build().parse(token).getPayload();
    }

}
