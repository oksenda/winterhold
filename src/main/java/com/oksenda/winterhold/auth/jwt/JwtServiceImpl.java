package com.oksenda.winterhold.auth.jwt;

import com.oksenda.winterhold.models.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Override
    public String generteToken(Account account) {
        return Jwts.builder()
                .subject(account.getUsername())
                .issuer("http://localhost:8081")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .claims()
                .add("username",account.getUsername())
                .and()
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Boolean isValid(String token, UserDetails userDetails) {
        return getClaims(token).get("username").equals(userDetails.getUsername()) && !isTokenExpiration(token);
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpiration(String token){
        return getClaims(token).getExpiration().before(new Date());
    }
}
