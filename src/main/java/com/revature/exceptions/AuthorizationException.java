package com.revature.exceptions;

/**
 * An Exception which is thrown when a User attempts to do an action they are not authorized to do.
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(){
        super("You don't have proper permissions to be taking this action.");
    }
}
