package com.revature.util;

import com.revature.dtos.PrincipalDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    private JwtConfig config;

    @Autowired
    public JwtValidator(JwtConfig config) {
        this.config = config;
    }

    public PrincipalDTO parseToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(config.getSigningKey())
                .parseClaimsJws(token)
                .getBody();

        int id = Integer.parseInt(claims.getId());
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        System.out.println("User ID: " + id);
        System.out.println("User Username: " + username);
        System.out.println("User Role: " + role);

        return new PrincipalDTO(id, username, role);

    }
}
