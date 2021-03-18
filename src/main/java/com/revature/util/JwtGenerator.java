package com.revature.util;


import com.revature.dtos.PrincipalDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * This class will generate a JWT for when a user logs in
 */
@Component
public class JwtGenerator {

    private final JwtConfig config;

    /**
     * Takes in a JWTConfig that configures a JWT
     * @param config the configuration object to configure JWTs for this application
     */
    @Autowired
    public JwtGenerator(final JwtConfig config) {
        this.config = config;
    }

    /**
     * Creates a JWT from user information
     * @param subject the principal dto of user information
     * @return a string representation of the token
     */
    public String createToken(final PrincipalDTO subject) {

        final long now = System.currentTimeMillis();

        final JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(subject.getId()))
                .setSubject(subject.getUsername())
                .claim("role", subject.getUserRole())
                .setIssuer("revature")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + (60 * 60 * 1000)))
                .signWith(config.getSignatureAlgorithm(), config.getSigningKey());

        return builder.compact();
    }
}
