package com.fszuberski.tweets.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JWTService {
    private final byte[] JWT_SECRET;
    private static JwtParser jwtParser;

    public JWTService() {
        JWT_SECRET = System.getenv("JWT_SECRET").getBytes(StandardCharsets.UTF_8);
    }

    public JWTService(byte[] secret) {
        if (secret == null || secret.length == 0) {
            throw new IllegalArgumentException(
                    String.format("Exception while initializing %s - JWT_SECRET cannot be null or empty", JWTService.class.getSimpleName()));
        }

        this.JWT_SECRET = secret;
    }

    public String createAndSignJWT(String subject) {
        Key key = Keys.hmacShaKeyFor(JWT_SECRET);
        JwtBuilder jwtBuilder = Jwts.builder();

        if (subject != null && subject.length() > 0) {
            jwtBuilder.setSubject(subject);
        }

        return jwtBuilder
                .setIssuedAt(new Date())
                .setIssuer("fszuberski.com")
                .signWith(key)
                .compact();
    }

    public boolean isValidSignedJWT(String jwt) {
        try {
            getJwtParser().parseClaimsJws(jwt);
        } catch (Exception e) {
            System.err.println("Exception while parsing JWT (JWS) Claims: " + jwt);
            return false;
        }

        return true;
    }

    public String getSubject(String jwt) {
        Jws<Claims> jwtClaims;
        try {
            jwtClaims = getJwtParser().parseClaimsJws(jwt);
        } catch(Exception e) {
            System.err.println("Exception while parsing JWT (JWS) Claims: " + jwt);
            return "";
        }

        return jwtClaims
                .getBody()
                .getSubject();
    }

    private JwtParser getJwtParser() {
        if (jwtParser == null) {
            jwtParser = Jwts
                    .parserBuilder()
                    .setSigningKey(JWT_SECRET)
                    .build();
        }

        return jwtParser;
    }
}
