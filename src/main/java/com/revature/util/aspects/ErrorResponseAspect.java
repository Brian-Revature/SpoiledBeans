package com.revature.util.aspects;

import com.revature.dtos.ErrorResponse;
import com.revature.exceptions.*;
import com.revature.util.ErrorResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 */
@Component
@RestControllerAdvice
public class ErrorResponseAspect {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAuthorizationException(AuthorizationException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidRequestException(InvalidRequestException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleResourcePersistenceException(ResourcePersistenceException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleSpoiledBeansException(SpoiledBeansException e){
        return ErrorResponseFactory.getInstance().generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
