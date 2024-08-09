package com.muse.lovely.WebsecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, expiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(jwt);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String jwt){
        return extractClaim(jwt, Claims::getSubject);
    }

    private Date extractExpirationDate(String jwt){
        return  extractClaim(jwt, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Boolean isTokenValid(String jwt, UserDetails userDetails){
        final String email = extractUsername(jwt);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }




}
