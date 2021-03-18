package com.revature.util;

import com.revature.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;

/**
 *Factory Singleton which generates ErrorResponse objects.
 */
public class ErrorResponseFactory {

    private static final ErrorResponseFactory errRespFactory = new ErrorResponseFactory();

    private ErrorResponseFactory() {
        super();
    }

    public static ErrorResponseFactory getInstance() {
        return errRespFactory;
    }

    public ErrorResponse generateErrorResponse(final int status,final String message) {
        return new ErrorResponse(status, message, System.currentTimeMillis());
    }

    public ErrorResponse generateErrorResponse(final HttpStatus status) {
        return new ErrorResponse(status.value(), status.toString(), System.currentTimeMillis());
    }
}
