package org.airtribe.AuthenticationAuthorizationC17.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.airtribe.AuthenticationAuthorizationC17.entity.User;


public class JwtUtil {
  public static String generateJwtToken(User user) {
    return Jwts.builder().subject(user.getUsername())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
        .setIssuedAt(new java.util.Date())
        .claim("roles", "ROLE_" + user.getRole())
        .claim("emailVerified", user.isEnabled())
        .signWith(SignatureAlgorithm.HS256, "airtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthentication")
        .compact();

  }

  public static Claims validateJwtToken(String jwtToken) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(
              "airtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthenticationairtribeTestingJwtTokenAuthentication")
          .build()
          .parseClaimsJws(jwtToken)
          .getPayload();

      return claims;
    } catch (io.jsonwebtoken.ExpiredJwtException e) {
      System.err.println("JWT token is expired: " + e.getMessage());
      return null;
    } catch (io.jsonwebtoken.SignatureException e) {
      System.err.println("Invalid JWT signature: " + e.getMessage());
      return null;
    } catch (io.jsonwebtoken.MalformedJwtException e) {
      System.err.println("Invalid JWT token: " + e.getMessage());
      return null;
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
      System.err.println("JWT token is unsupported: " + e.getMessage());
      return null;
    } catch (IllegalArgumentException e) {
      System.err.println("JWT claims string is empty: " + e.getMessage());
      return null;
    }
  }
}
