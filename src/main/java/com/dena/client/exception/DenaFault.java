package com.dena.client.exception;

import java.util.List;

/**
 * @author Javad Alimohammadi [<bs.alimohammadi@yahoo.com>]
 */
public class DenaFault extends RuntimeException {
    private int httpStatusCode;

    private String errorCode;

    private List<String> messages;

    public DenaFault(String message) {
        super(message);
    }

    public DenaFault(String message, int httpStatusCode, String errorCode, Throwable cause) {
        super(message, cause);
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<String> getMessages() {
        return messages;
    }
}
