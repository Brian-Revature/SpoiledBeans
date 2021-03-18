package com.revature.exceptions;

/**
 *Exception which is thrown when a user makes an invalid request of the service or bacnkend.
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid request made!");
    }

    public InvalidRequestException(final String message) {
        super(message);
    }

}
