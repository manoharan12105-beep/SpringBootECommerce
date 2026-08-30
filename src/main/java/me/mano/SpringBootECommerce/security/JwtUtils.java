package me.mano.SpringBootECommerce.security;


import java.security.Key;
import java.util.Date;
import java.util.Base64.Decoder;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${spring.app.jwtSecret}")
  private String jwtSecret;

  @Value("${spring.app.jwtExpirationMs}")
  private int jwtExpirationMs;


  // Get Authorization Header
  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    logger.debug("Authorization Header: {}", bearerToken);
    if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  // Generating Token from Username 
  public String generateTokenFromUsername(UserDetails UserDetails) {
    String username = UserDetails.getUsername();
    return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key())
                .compact();
  }


  // Getting username from Jwt Token
  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
               .verifyWith(key())
               .build().parseSignedClaims(token)
               .getPayload().getSubject();
  }


  // Generate Signing key
  public SecretKey key() {
    return Keys.hmacShaKeyFor(
      Decoders.BASE64.decode(jwtSecret)
    );
  }


  // Validate JWT Token
  public boolean validateToken(String authToken) {
    try {
      System.out.println("Validate");
      Jwts.parser()
          .verifyWith(key())
          .build()
          .parseSignedClaims(authToken);
          
      return true;
    } catch (MalformedJwtException exception) {
      logger.error("Invalid JWT token: {}", exception.getMessage());
    } catch (ExpiredJwtException exception) {
      logger.error("JWT token is expired: {}", exception.getMessage());
    } catch (UnsupportedJwtException exception) {
      logger.error("JWT token is unsupported: {}", exception.getMessage());
    } catch (IllegalArgumentException exception) {
      logger.error("JWT claims string is empty: {}", exception.getMessage());
    }

    return false;
  }
}
