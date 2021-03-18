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

    /**
     *Method to returned a generated JWT
     * @param subject User who should have a JWT generated for.
     * @return String representation of JWT.
     */
    public String generateToken(final PrincipalDTO subject) {
        return tokenGenerator.createToken(subject);
    }

    /**
     * Method to check if a given token is valid
     * @param token token to check if it is valid.
     * @return true = valid. false = not valid.
     */
    public boolean isTokenValid(final String token) {
        final PrincipalDTO principal = tokenValidator.parseToken(token);
        return principal != null;
    }

    /**
     * MEthod to extract the user role from a given JWT.
     * @param token toekn to extract the user role from.
     * @return String representation of user role.
     */
    public String getAuthorities(final String token) {
        return getPrincipal(token).getUserRole();

    }

    /**
     * Method to extract user id form a given JWT.
     * @param token JWT to extract user id from.
     * @return user id.
     */
    public int getUserId(final String token){
        return getPrincipal(token).getId();
    }

    /**
     * MEthod to extract username from a given JWT.
     * @param token JWT to extract user name from.
     * @return string of user name from JWT.
     */
    public String getUsername(final String token){
        return getPrincipal(token).getUsername();
    }

    /**
     * MEthod to get a principle DTO from a given JWT.
     * @param token JEW to parse into a principleDTO.
     * @return principleDTO with information parsed from the JWT.
     */
    private PrincipalDTO getPrincipal(final String token){
       final PrincipalDTO principal = tokenValidator.parseToken(token);

        if (principal == null) {
            throw new RuntimeException("Principal within token was null!");
        }

        return principal;
    }

}
