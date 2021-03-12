package com.revature.util;


import com.revature.dtos.PrincipalDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    private JwtConfig config;

    @Autowired
    public JwtGenerator(JwtConfig config) {
        this.config = config;
    }

    public String createToken(PrincipalDTO subject) {

        long now = System.currentTimeMillis();

        System.out.println("User ID: " + subject.getId());
        System.out.println("User Username: " + subject.getUsername());
        System.out.println("User Role: " + subject.getUserRole());

        JwtBuilder builder = Jwts.builder()
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
