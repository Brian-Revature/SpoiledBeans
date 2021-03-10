package com.revature.util;


import com.revature.dtos.PrincipalDTO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
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

        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(subject.getId()))
                .setSubject(subject.getUsername())
                .claim("role", subject.getRole())
                .setIssuer("revature")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + (60 * 60 * 1000)))
                .signWith(config.getSignatureAlgorithm(), config.getSigningKey());

        return builder.compact();

    }
}
