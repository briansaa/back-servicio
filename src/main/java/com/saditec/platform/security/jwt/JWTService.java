package com.saditec.platform.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saditec.platform.security.auth.entity.TUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time-minutes}")
    private Long expirationTime;

    @Getter
    @Value("${security.jwt.prefix}")
    private String prefix;

    private final ObjectMapper mapper = new ObjectMapper();

    public String generateToken(Collection<? extends GrantedAuthority> authorities, TUserEntity userDetails) {
        return Jwts.builder()
                .claim("authorities", authorities)
                .subject(userDetails.getIdentifier())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime()))
                .signWith(getSigningKey())
                .compact();
    }

    public long expirationTime() {
        return expirationTime * 60000;
    }

    public boolean isValidToken(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.after(new Date());
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public List<?> getAuthorities(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build().parseSignedClaims(token)
                .getPayload();

        ArrayList<?> objects = mapper.convertValue(claims.get("authorities"), ArrayList.class);
        return objects.stream().map(authority -> mapper.convertValue(authority, LinkedHashMap.class).get("authority"))
                .toList();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
