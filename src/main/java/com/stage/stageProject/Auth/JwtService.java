package com.stage.stageProject.Auth;
import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stage.stageProject.UserMgmt.UserToken;
import com.stage.stageProject.View.UserController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j; 

@Slf4j
@Component
public class JwtService { 
    public static final String SECRET = "9D0EB6B1C2E1FAD0F53A248F6C3B5E4E2F6D8G3H1I0J7K4L1M9N2O3P5Q0R7S9T1U4V2W6X0Y3Z"; 
    private int jwtExpirationMS = 600000;
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);
    public String generateToken(String userName) { 
        return createToken(userName); 
    } 
    
    
    private String createToken(String userName) { 
        return Jwts.builder() 
                .setSubject(userName) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMS))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact(); 
    } 
    
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
    
	public String extractUsername(String token) { 
		return validateToken(token) ? 
				Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject()
				:
				"Error";
    } 

    public Boolean validateToken(String token) { 
        try {
        	Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
        	return true;
        } catch (MalformedJwtException e) {
          logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
          logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
          logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
          logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        UserToken.invalidateToken(token);
        return false;
    } 
}
