package com.dena.client.exception;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public abstract class DenaFault extends RuntimeException {

    protected ErrorCode errorCode;

    public DenaFault(String message) {
        super(message);
    }

    public DenaFault(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract ErrorCode getErrorCode();
}
