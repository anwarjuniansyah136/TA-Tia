package com.rental.Inventory.utils;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.rental.Inventory.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
    private String secretKey = "ecommerce";
    private Long accessTokenValidityMinutes = 60L;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private JwtParser jwtParser;

    public JwtUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    public String generateToken(Users auth){
        Claims claims = Jwts.claims().setSubject(auth.getUsername());
        claims.put("email", auth.getUsername());
        claims.put("role", auth.getRoles().getRoleName().toLowerCase());

        Date tokenCreateTime = new Date();
        Date tokenValidaty = new Date(tokenCreateTime.getTime()+TimeUnit.MINUTES.toMillis(accessTokenValidityMinutes));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidaty)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    private Claims parseJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Optional<String> resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader(TOKEN_HEADER);
        if(bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)){
            return Optional.of(bearerToken.substring(TOKEN_PREFIX.length()));
        }
        return Optional.empty();
    }

    public Claims resolveClaims(String token){
        try {
            return parseJwtClaims(token);
        } catch (ExpiredJwtException e) {
            throw e;
        } catch(Exception e){
            throw e;
        }
    }

    public boolean validateClaims(Claims claims){
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

}
