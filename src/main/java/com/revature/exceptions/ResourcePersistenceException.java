package com.revature.exceptions;

/**
 * Exception which is thrown when a problem arises with persisting data.
 */
public class ResourcePersistenceException extends RuntimeException{

    public ResourcePersistenceException(){
        super("Resource not persisted.");
    }

    public ResourcePersistenceException(final String message){
        super(message);
    }
}
