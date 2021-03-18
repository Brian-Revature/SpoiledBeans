package com.revature.services;

import com.revature.dtos.PrincipalDTO;
import com.revature.util.JwtGenerator;
import com.revature.util.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service CLass which handles A user logging in.
 * User can log into an existing account and is given a JWT upon successfully logging in.

 */
@Service
public class AuthService {

    private final JwtGenerator tokenGenerator;
    private final JwtValidator tokenValidator;

    @Autowired
    public AuthService(final JwtGenerator tokenGenerator,final JwtValidator tokenValidator) {
        this.tokenGenerator = tokenGenerator;
        this.tokenValidator = tokenValidator;
    }

    public String generateToken(final PrincipalDTO subject) {
        return tokenGenerator.createToken(subject);
    }

    public boolean isTokenValid(final String token) {
        PrincipalDTO principal = tokenValidator.parseToken(token);
        return principal != null;
    }

    public String getAuthorities(final String token) {
        return getPrincipal(token).getUserRole();

    }

    public int getUserId(final String token){
        return getPrincipal(token).getId();
    }

    public String getUsername(final String token){
        return getPrincipal(token).getUsername();
    }

    private PrincipalDTO getPrincipal(final String token){
        PrincipalDTO principal = tokenValidator.parseToken(token);

        if (principal == null) {
            throw new RuntimeException("Principal within token was null!");
        }

        return principal;
    }

}
