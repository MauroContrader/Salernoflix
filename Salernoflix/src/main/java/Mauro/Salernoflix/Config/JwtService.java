package Mauro.Salernoflix.Config;

import Mauro.Salernoflix.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "yv9xcfnYGpxD5gNUZsoAfgOVDQNm4VZ9TqO1VPibugw";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, Boolean rememberMe) {
        return generateToken(new HashMap<>(), userDetails, rememberMe);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        Boolean rememberMe) {
        return Jwts
            .builder()
            .setId(UUID.randomUUID().toString())
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .claim("roles", ((User) userDetails).getRole())
            .claim("rememberMe", rememberMe)
            .setIssuedAt(new Date())
            .setExpiration(rememberMe ? new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7) : new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(SignatureAlgorithm.HS256, getSigningKey())
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parser()
            .setSigningKey(getSigningKey())
            .parseClaimsJws(token)
            .getBody();
    }

    public Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
