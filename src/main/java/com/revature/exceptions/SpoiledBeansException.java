package com.revature.exceptions;

/**
 * A generic Exception which is thrown when there is not a more specific or precise Exception to be thrown.
 */
public class SpoiledBeansException extends RuntimeException{

    public SpoiledBeansException(final Throwable e) {
        super("Your beans were spoiled! Check the error log for more info.", e);
    }
}
