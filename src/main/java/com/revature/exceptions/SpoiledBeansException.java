package com.revature.exceptions;

public class SpoiledBeansException extends RuntimeException{

    public SpoiledBeansException(Throwable e) {
        super("Your beans were spoiled! Check the error log for more info.", e);
    }
}
