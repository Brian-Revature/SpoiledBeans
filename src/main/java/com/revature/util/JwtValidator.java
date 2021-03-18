package com.revature.util;

import com.revature.dtos.PrincipalDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class will validate JWT (JSON Web Tokens) from the a requester
 */
@Component
public class JwtValidator {

    private final JwtConfig config;

    /**
     * Constructor wiring in a JWTConfig object
     * @param config the JWTConfig object that configured JWTs from this application
     */
    @Autowired
    public JwtValidator(final JwtConfig config) {
        this.config = config;
    }

    /**
     * Parses a string representing the token from a request cookie
     * @param token a string representing the encrypted token
     * @return a PrincipalDTO that sets the user ID, username and user role
     */
    public PrincipalDTO parseToken(final String token) {

        final Claims claims = Jwts.parser()
                .setSigningKey(config.getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        final int id = Integer.parseInt(claims.getId());
        final String username = claims.getSubject();
        final String role = claims.get("role", String.class);

        return new PrincipalDTO(id, username, role);

    }
}
