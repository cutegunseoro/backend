package com.ssafy.travlog.api.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access_token_expiration}") long accessTokenExpiration
    ) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public String createAccessToken(String publicId) {
        return createToken(publicId, "access", accessTokenExpiration);
    }

    public String createToken(String publicId, String tokenType, long expTime) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expTime);

        return Jwts.builder()
                .signWith(key)
                .issuer("travlog")
                .subject(publicId)
                .audience().add(tokenType).and() // use to identify token type
                .issuedAt(issuedAt)
                .expiration(expiration)
                .id(UUID.randomUUID().toString()) // to use later for blacklist
                .compact();
    }
}
