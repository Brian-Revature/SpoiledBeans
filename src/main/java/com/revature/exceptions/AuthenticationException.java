package com.revature.exceptions;

/**
 * An Exception which is thrown when a problem with authenticating a user is encountered.
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Authentication Failed!");
    }

    public AuthenticationException(final String message){
        super (message);
    }

}
