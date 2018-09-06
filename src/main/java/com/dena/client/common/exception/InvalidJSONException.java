package com.dena.client.common.exception;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@gmail.com>]
 */
public class InvalidJSONException extends RuntimeException {

    public InvalidJSONException(String message) {
        super(message);
    }

    public InvalidJSONException(String message, Throwable cause) {
        super(message, cause);
    }
}
