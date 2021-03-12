package com.revature.services;

import com.revature.dtos.PrincipalDTO;
import com.revature.util.JwtGenerator;
import com.revature.util.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtGenerator tokenGenerator;
    private JwtValidator tokenValidator;

    @Autowired
    public AuthService(JwtGenerator tokenGenerator, JwtValidator tokenValidator) {
        this.tokenGenerator = tokenGenerator;
        this.tokenValidator = tokenValidator;
    }

    public String generateToken(PrincipalDTO subject) {
        return tokenGenerator.createToken(subject);
    }

    public boolean isTokenValid(String token) {
        PrincipalDTO principal = tokenValidator.parseToken(token);
        return principal != null;
    }

    public String getAuthorities(String token) {
        return getPrincipal(token).getUserRole();

    }

    public int getUserId(String token){
        return getPrincipal(token).getId();
    }

    public String getUsername(String token){
        return getPrincipal(token).getUsername();
    }

    private PrincipalDTO getPrincipal(String token){
        PrincipalDTO principal = tokenValidator.parseToken(token);

        if (principal == null) {
            throw new RuntimeException("Principal within token was null!");
        }

        return principal;
    }

}
