package com.revature.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(){
        super("You don't have proper permissions to be taking this action.");
    }
}
