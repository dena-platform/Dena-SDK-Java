package com.dena.client.common.exception;

import com.dena.client.common.web.HttpClient.dto.response.ErrorResponse;

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

    public static DenaFault makeException(String message, Throwable cause) {
        return new DenaFault(message, cause);
    }
}
