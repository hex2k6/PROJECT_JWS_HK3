package com.example.medic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private Key getSignKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(
                        secretKey
                );

        return Keys.hmacShaKeyFor(
                keyBytes
        );
    }

    /**
     * Generate Access Token
     */
    public String generateAccessToken(
            String email
    ) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(
                        new Date()
                )
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + accessExpiration
                        )
                )
                .signWith(
                        getSignKey()
                )
                .compact();
    }

    /**
     * Generate Refresh Token
     */
    public String generateRefreshToken(
            String email
    ) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(
                        new Date()
                )
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + refreshExpiration
                        )
                )
                .signWith(
                        getSignKey()
                )
                .compact();
    }

    /**
     * Extract username
     */
    public String extractUsername(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    /**
     * Extract any claim
     */
    public <T> T extractClaim(

            String token,

            Function<Claims, T>
                    claimsResolver
    ) {

        Claims claims =
                extractAllClaims(
                        token
                );

        return claimsResolver.apply(
                claims
        );
    }

    /**
     * Extract all claims
     */
    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parser()
                .verifyWith(
                        (SecretKey)
                                getSignKey()
                )
                .build()
                .parseSignedClaims(
                        token
                )
                .getPayload();
    }

    /**
     * Validate token
     */
    public boolean isTokenValid(

            String token,

            String email
    ) {

        String username =
                extractUsername(
                        token
                );

        return username.equals(
                email
        ) && !isTokenExpired(
                token
        );
    }

    /**
     * Check expired
     */
    private boolean isTokenExpired(
            String token
    ) {

        return extractClaim(
                token,
                Claims::getExpiration
        ).before(
                new Date()
        );
    }

    /**
     * Get remaining expiration
     * (for Redis blacklist)
     */
    public long getExpiration(
            String token
    ) {

        Date expiration =
                extractClaim(
                        token,
                        Claims::getExpiration
                );

        return expiration.getTime()
                - System.currentTimeMillis();
    }
}