package com.revature.exceptions;

/**
 * Exception which is thrown when user request a resource which can not be found inside of the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resource(s) found");
    }

}