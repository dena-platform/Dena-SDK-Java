package com.dena.client.exception;

import com.dena.client.service.web.HttpClient.dto.response.ErrorResponse;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaFault extends RuntimeException {
    private ErrorResponse errorResponse;


    public DenaFault(String message, ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public DenaFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
